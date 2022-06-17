package ru.mahov.mvc.models;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Book {

    private int id;
    private int person_id;
    @Size(min = 1, max = 100, message = "Название книги должно состоять минимум из одного символа, максимум из 100")
    private String title;
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+", message = "ФИО введене в неверном формате.Фамилия,имя должны быть введены с заглавной буквы через пробел")
    private String author;
    private int year;

    public Book() {
    }

    public Book(int id, int person_id, String title, String author, int year) {
        this.id = id;
        this.person_id = person_id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

}
