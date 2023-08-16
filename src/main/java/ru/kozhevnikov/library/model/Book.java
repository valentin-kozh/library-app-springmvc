package ru.kozhevnikov.library.model;

import java.util.List;

public class Book {
    private int bookId;
    private Integer personId;
    private String name;
    private String author;
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
