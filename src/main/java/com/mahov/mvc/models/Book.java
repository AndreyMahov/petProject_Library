package com.mahov.mvc.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @Size(min = 1, max = 100, message = "Название книги должно состоять минимум из одного символа, максимум из 100")
    private String title;

    @Column(name = "author")
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+", message = "ФИО введене в неверном формате.Фамилия,имя должны быть введены с заглавной буквы через пробел")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "hold_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holdDate;

    @Transient
    private boolean holdPeriodEnded;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
