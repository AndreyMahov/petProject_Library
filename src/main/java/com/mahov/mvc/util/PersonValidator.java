package com.mahov.mvc.util;

import com.mahov.mvc.models.Person;
import com.mahov.mvc.services.PeopleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;
import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private static final int MIN_YEAR = 14;
    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person updatePerson = (Person) o;
        Optional<Person> checkNamePerson = peopleService.findByFullname(updatePerson.getFullName());
        Optional<Person> checkLoginPerson = peopleService.findByLogin(updatePerson.getLogin());

        if (checkNamePerson.isPresent() && updatePerson.getId() != checkNamePerson.get().getId()) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }
        if (checkLoginPerson.isPresent() && updatePerson.getId() != checkLoginPerson.get().getId()) {
            errors.rejectValue("login", "", "Человек с таким логином уже существует");
        }
        if (updatePerson.getYear() > (Year.now().getValue() - MIN_YEAR)) {
            errors.rejectValue("year", "", "Регистрация только с 14 лет");
        }
    }

}
