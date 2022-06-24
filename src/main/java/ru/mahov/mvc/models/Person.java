package ru.mahov.mvc.models;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public @Data
class Person {

    @OneToMany(mappedBy = "owner")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    List<Book> books;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    @Size(max = 100, message = "Максимум 100 символол")
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+", message = "ФИО введено в неверном формате.Фамилия,имя и отчество должны начинаться с заглавной буквы и введениы через пробел")
    private String fullName;

    @Column(name = "yearOfBirth")
    @Min(value = 1922, message = "поле не может быть меньше чем 1922")
    private int yearOfBirth;

    public Person() {
    }

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                '}';
    }

}
