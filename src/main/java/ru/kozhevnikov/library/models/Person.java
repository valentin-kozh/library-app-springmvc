package ru.kozhevnikov.library.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;
    @Column(name = "name")
    @NotBlank(message = "Поле ФИО не может быть пустым")
    @Size(min = 2, max = 100, message = "Количество символов в ФИО должно бытть между 2 и 100")
    @Pattern(regexp = "^[^0-9]*$", message = "В ФИО не могут использоваться цифры")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "ФИО должно быть указано в формате: Иванов Иван Иванович")
    private String name;
    @Column(name = "year")
    @Max(value = 2022, message = "Год рождения введен некорректно")
    private int year;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }
    public Person(String name, int year) {
        this.name = name;
        this.year = year;
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public void addBook(Book book){
        if (this.books == null){
            this.books = new ArrayList<>();
        }
        this.books.add(book);
        book.setOwner(this);
    }
}
