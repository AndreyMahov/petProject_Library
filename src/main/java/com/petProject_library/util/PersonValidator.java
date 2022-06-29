package com.petProject_library.util;

import com.petProject_library.models.Person;
import com.petProject_library.services.PeopleService;
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
        Optional<Person> dbPerson = peopleService.findByFullname(updatePerson.getFullName());

        if (dbPerson.isPresent() && updatePerson.getId() != dbPerson.get().getId()) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }
        if (updatePerson.getYear() > (Year.now().getValue() - MIN_YEAR)) {
            errors.rejectValue("year", "", "Регистрация только с 14 лет");
        }
    }

}
