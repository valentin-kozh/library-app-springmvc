package ru.kozhevnikov.library.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;
    @Column(name = "name")
    @NotBlank(message = "Введите название книги")
    private String name;
    @Column(name = "author")
    @NotBlank(message = "Поле обязательно для заполнения")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Автор должен быть указан в формате (Брюс Эккель)")
    private String author;
    @Column(name = "year")
    @Max(value = 2023, message = "Год издания введен некорректно")
    private int year;
    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "was_taken")
    @CreationTimestamp
    private LocalDateTime wasTakenAt;

    @Transient
    private boolean isOverdue;
    @Transient
    private int daysUntilReturn;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int bookId) {
        this.id = bookId;
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

    public Person getOwner() {
        return owner;
    }
    public LocalDateTime getWasTakenAt() {
        return wasTakenAt;
    }

    public void setWasTakenAt(LocalDateTime wasTakenAt) {
        this.wasTakenAt = wasTakenAt;
    }

    public boolean isOverdue() {
        return isOverdue;
    }
    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public int getDaysUntilReturn() {
        return daysUntilReturn;
    }

    public void setDaysUntilReturn(int daysUntilReturn) {
        this.daysUntilReturn = daysUntilReturn;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
