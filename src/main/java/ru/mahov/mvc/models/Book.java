package ru.mahov.mvc.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public @Data
class Book {

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
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", holdDate=" + holdDate +
                ", holdPeriodEnded=" + holdPeriodEnded +
                '}';
    }

    public boolean isHoldPeriodEnded() {
        return holdPeriodEnded;
    }

    public void setHoldPeriodEnded(boolean holdPeriodEnded) {
        this.holdPeriodEnded = holdPeriodEnded;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }
}
