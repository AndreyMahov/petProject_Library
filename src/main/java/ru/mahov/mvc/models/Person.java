package ru.mahov.mvc.models;



import javax.validation.constraints.Min;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class Person {
    private static  int minYear = 14;


    private  int id;
    @Size(max = 100, message = "Максимум 100 символол")
   @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+", message = "ФИО введене в неверном формате.Фамилия,имя и отчество должны начинаться с заглавной буквы и введениы через пробел")
    private String fullName;
    @Min(value = 1922,message = "поле не может быть меньше чем 1922")
    private int yearOfBirth;


    public Person() {
    }

    public Person(int id, String fullName, int yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getMinYear() {
        return minYear;
    }

    public static void setMinYear(int minYear) {
        Person.minYear = minYear;
    }


}
