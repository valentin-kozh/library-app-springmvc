package ru.kozhevnikov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kozhevnikov.library.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByName(String name);
    List<Book> findByNameStartingWith(String beginningOfName);
}
