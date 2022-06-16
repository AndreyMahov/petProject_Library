package ru.mahov.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.mahov.mvc.rowMappers.BookRowMapper;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = bookRowMapper;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }


    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(fullname,yearOfBirth) VALUES (?,?)", person.getFullName(), person.getYearOfBirth());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public Person show(int id) {

        return jdbcTemplate.query("SELECT *FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Person updatePerson) {

        jdbcTemplate.update("UPDATE person set fullname=?,yearOfBirth=? WHERE id=?", updatePerson.getFullName(), updatePerson.getYearOfBirth(), id);
    }

    public List<Book> showBooks(int id) {
        return jdbcTemplate.query("SELECT*FROM book JOIN person ON person.id = book.person_id WHERE person_id=?", bookRowMapper, id);

    }


    public Optional<Person> getByName(String fullName) {
        return jdbcTemplate.query("SELECT*FROM person WHERE fullname=?", new BeanPropertyRowMapper<>(Person.class), fullName).stream().findAny();

    }
}



