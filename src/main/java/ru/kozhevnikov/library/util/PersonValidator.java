package ru.kozhevnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kozhevnikov.library.models.Person;
import ru.kozhevnikov.library.modelDAO.PersonDAO;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.show(person.getName()).isPresent())
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");
        if (person.getYear() <= 1900) errors.rejectValue("year", "", "Год указан неверно");
    }

    public void validate(Object target, Errors errors, int id) {
        Person person = (Person) target;
        if (personDAO.show(person.getName()).isPresent() && !person.getName().equals(personDAO.show(id).getName()))
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");
        if (person.getYear() <= 1900) errors.rejectValue("year", "", "Год указан неверно");
    }
}
