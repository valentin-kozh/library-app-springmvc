package ru.kozhevnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kozhevnikov.library.models.Book;
import ru.kozhevnikov.library.services.BooksService;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;
    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }


    public void validate(Object target, Errors errors, int id) {
        Book book = (Book) target;
        if (booksService.findOne(book.getName()).isPresent() && !book.getName().equals(booksService.findOne(id).getName()))
            errors.rejectValue("name", "", "Книга уже добавлена в спсиок");
    }
    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (booksService.findOne(book.getName()).isPresent())
            errors.rejectValue("name", "", "Книга уже добавлена в спсиок");
    }
}

