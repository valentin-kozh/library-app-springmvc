package ru.kozhevnikov.library.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.library.models.Book;
import ru.kozhevnikov.library.models.Person;
import ru.kozhevnikov.library.repositories.BooksRepository;
import ru.kozhevnikov.library.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Valentin Kozhevnikov
 */
@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public List<Book> findByOwner(Person owner){
        return booksRepository.findByOwner(owner);
    }
    public Book findOne(int id){
        return booksRepository.findById(id).orElse(null);
    }
    public Optional<Book> findOne(String name){
        return booksRepository.findByName(name);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    @Transactional
    public Optional<Person> getBookOwner(int id){
        Book book = booksRepository.findById(id).orElse(null);
        return Optional.ofNullable(book.getOwner());
    }
    @Transactional
    public void assign(int bookId, Person person){
        int personID = person.getId();
        Person personToUpdate = peopleRepository.findById(personID).get();
        Book assignedBook = booksRepository.findById(bookId).get();
        personToUpdate.addBook(assignedBook);
    }
    @Transactional
    public void release(int id){
        booksRepository
                .findById(id)
                .get()
                .setOwner(null);
    }

    @Transactional
    public Page<Book> findPaginated(Pageable pageable, boolean sortByYear) {
        List<Book> books;
        if(sortByYear){
            books = booksRepository.findAll(Sort.by("year"));
        }
        else {
            books = findAll();
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = pageSize*currentPage;

        List<Book> list;
        if (startItem > books.size()){
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startItem+pageSize, books.size());
            list = books.subList(startItem,toIndex);
        }
        Page<Book> bookPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), books.size());
        return bookPage;
    }
}
