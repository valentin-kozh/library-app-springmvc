package ru.kozhevnikov.library.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class Book {
    private int bookId;
    private Integer personId;
    @NotBlank(message = "Введите название книги")
    private String name;
    @NotBlank(message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Автор должен быть указан в формате (Брюс Эккель)")
    private String author;
    @Max(value = 2023, message = "Год издания введен некорректно")
    private int year;

    public Book() {
    }

    public Book(int bookId, Integer personId, String name, String author, int year) {
        this.bookId = bookId;
        this.personId = personId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
