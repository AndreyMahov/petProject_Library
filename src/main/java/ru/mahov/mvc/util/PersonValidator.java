package ru.mahov.mvc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mahov.mvc.dao.PersonDAO;
import ru.mahov.mvc.models.Person;

import java.time.Year;


@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.getByName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }

        if (person.getYearOfBirth() > (Year.now().getValue() - Person.getMinYear())) {
            errors.rejectValue("yearOfBirth", "", "Регистрация только с 14 лет");
        }

    }


}
