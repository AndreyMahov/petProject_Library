package ru.mahov.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mahov.mvc.RowMappers.BookRowMapper;
import ru.mahov.mvc.models.Book;

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


    public List<Book> showAll() {
        return jdbcTemplate.query("SELECT * FROM book",bookRowMapper);
    }

    public Book showById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?",
                new Object[]{id},bookRowMapper).stream().findAny().orElse(null);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?"
                ,book.getTitle(),book.getAuthor(),book.getYear(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE  FROM book WHERE id=?",id);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(title,author,year) VALUES (?,?,?)"
                ,book.getTitle(),book.getAuthor(),book.getYear());
    }

    public Optional<Book> checkBook(int id){
        return jdbcTemplate.query("SELECT*FROM person join  book ON person.id=book.person_id WHERE book.id=?",
                bookRowMapper,id).stream().findAny();

    }

    public void giveBook(int person_id,int book_id) {
       jdbcTemplate.update("UPDATE book set person_id=? WHERE id=? ",person_id,book_id);
    }

    public void takeBook(int id) {
        jdbcTemplate.update("UPDATE book set person_id=NULL  WHERE id=? ",id);
    }


}
