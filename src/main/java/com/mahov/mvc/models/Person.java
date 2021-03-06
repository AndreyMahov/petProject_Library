package com.mahov.mvc.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
@Component
@Getter
@Setter
@ToString
public class Person {

    @OneToMany(mappedBy = "owner")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ToString.Exclude
    List<Book> books;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    @Size(max = 100, message = "Максимум 100 символол")
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+", message = "ФИО введено в неверном формате.Фамилия,имя и отчество должны начинаться с заглавной буквы и введениы через пробел")
    private String fullName;

    @Column(name = "yearofbirth")
    @Min(value = 1922, message = "поле не может быть меньше чем 1922")
    private int year;

    @Column(name = "login")
    private String login;

   //TODO make password in chars Array
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public Person() {
    }

    public Person(String fullName, int year) {
        this.fullName = fullName;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
