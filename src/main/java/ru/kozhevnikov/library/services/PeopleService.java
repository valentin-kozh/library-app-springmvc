package ru.kozhevnikov.library.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.library.models.Person;
import ru.kozhevnikov.library.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Valentin Kozhevnikov
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
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
