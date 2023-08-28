package ru.kozhevnikov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kozhevnikov.library.models.Person;
import ru.kozhevnikov.library.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.findOne(person.getName()).isPresent())
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");
        if (person.getYear() <= 1900) errors.rejectValue("year", "", "Год указан неверно");
    }

    public void validate(Object target, Errors errors, int id) {
        Person person = (Person) target;
        if (peopleService.findOne(person.getName()).isPresent() && !person.getName().equals(peopleService.findOne(id).getName()))
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");
        if (person.getYear() <= 1900) errors.rejectValue("year", "", "Год указан неверно");
    }
}
