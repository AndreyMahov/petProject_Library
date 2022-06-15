package ru.mahov.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;
import ru.mahov.mvc.rowMappers.BookRowMapper;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = bookRowMapper;
    }


    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", bookRowMapper);
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?",
                new Object[]{id}, bookRowMapper).stream().findAny().orElse(null);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?"
                , book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE  FROM book WHERE id=?", id);
    }

    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO book(title,author,year) VALUES (?,?,?)"
                , book.getTitle(), book.getAuthor(), book.getYear());
    }


    public void giveBook(int person_id, int book_id) {
        jdbcTemplate.update("UPDATE book set person_id=? WHERE id=? ", person_id, book_id);
    }

    public void takeBook(int id) {
        jdbcTemplate.update("UPDATE book set person_id=NULL  WHERE id=? ", id);
    }


    public Optional<Person> getOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id " +
                        "WHERE Book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();

    }
}
