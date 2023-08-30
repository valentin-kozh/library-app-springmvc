package ru.kozhevnikov.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kozhevnikov.library.models.Book;
import ru.kozhevnikov.library.models.Person;
import ru.kozhevnikov.library.repositories.BooksRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Valentin Kozhevnikov
 */
@Service
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Book> findOne(String name) {
        return booksRepository.findByName(name);
    }


    public List<Book> findByNameStartingWith(String beginningOfName) {
        if (beginningOfName.isBlank()){
            return Collections.emptyList();
        }
        beginningOfName = beginningOfName.substring(0,1).toUpperCase()+beginningOfName.substring(1);
        return booksRepository.findByNameStartingWith(beginningOfName);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
        book.setWasTakenAt(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        updatedBook.setOwner(findOne(id).getOwner());
        updatedBook.setWasTakenAt(findOne(id).getWasTakenAt());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void assign(int bookId, Person person) {
        booksRepository.findById(bookId).ifPresent(book -> {
            book.setOwner(person);
            book.setWasTakenAt(LocalDateTime.now());
        });
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setWasTakenAt(null);
        });
    }

    @Transactional
    public Page<Book> findPaginated(Pageable pageable, boolean sortByYear) {
        List<Book> books;
        if (sortByYear) {
            books = booksRepository.findAll(Sort.by("year"));
        } else {
            books = findAll();
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = pageSize * currentPage;

        List<Book> list;
        if (startItem > books.size()) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }
        Page<Book> bookPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), books.size());
        return bookPage;
    }
}
