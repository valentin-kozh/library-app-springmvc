package ru.kozhevnikov.library.models;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private int personId;
    @NotBlank(message = "Поле ФИО не может быть пустым")
    @Size(min = 2, max = 100, message = "Количество символов в ФИО должно бытть между 2 и 100")
    @Pattern(regexp = "^[^0-9]*$", message = "В ФИО не могут использоваться цифры")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "ФИО должно быть указано в формате: Иванов Иван Иванович")
    private String name;
    @Max(value = 2022, message = "Год рождения введен некорректно")
    private int year;

    private List<Book> takenBooks = new ArrayList<>();

    public Person() {
    }

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public List<Book> getTakenBooks() {
        return takenBooks;
    }

    public void setTakenBooks(List<Book> takenBooks) {
        this.takenBooks = takenBooks;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
