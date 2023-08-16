package ru.kozhevnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kozhevnikov.library.model.Book;
import ru.kozhevnikov.library.modelDAO.BookDAO;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;
    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDAO.show(book.getName()).isPresent())
            errors.rejectValue("name", "", "Книга уже добавлена в спсиок");
    }
}
