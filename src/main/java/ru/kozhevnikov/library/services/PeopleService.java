package ru.kozhevnikov.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.library.models.Book;
import ru.kozhevnikov.library.models.Person;
import ru.kozhevnikov.library.repositories.BooksRepository;
import ru.kozhevnikov.library.repositories.PeopleRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Valentin Kozhevnikov
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }
    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(book -> {
                LocalDateTime currentTime = LocalDateTime.now();
                int daysWasTaken = (int) Duration.between(book.getWasTakenAt(),currentTime).toDays();
                book.setDaysUntilReturn(10-daysWasTaken < 0 ? 0 : 10-daysWasTaken);
                if (daysWasTaken > 10) {
                    book.setOverdue(true);
                }
            });
            return person.get().getBooks();
        }
        else return Collections.emptyList();
    }
    public Optional<Person> findOne(String name) {
        return peopleRepository.findByName(name);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.findById(id).get().getBooks().forEach(book -> {
            book.setWasTakenAt(null);
        });
        peopleRepository.deleteById(id);
    }

    @Transactional
    public Page<Person> findPaginated(Pageable pageable, boolean sortByName, boolean sortByYear) {
        List<Person> people;
        if(sortByName && !sortByYear) {
           people = peopleRepository.findAll(Sort.by("name"));
        }
        else if (!sortByName && sortByYear) {
            people = peopleRepository.findAll(Sort.by("year"));
        }
        else {
            people = findAll();
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = pageSize*currentPage;

        List<Person> list;

        if (people.size() < startItem){
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startItem+pageSize, people.size());
            list = people.subList(startItem, toIndex);
        }
        return new PageImpl<>(list,PageRequest.of(currentPage, pageSize), people.size());
    }
}
