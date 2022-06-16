package ru.mahov.mvc.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mahov.mvc.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setYear(rs.getInt("year"));
        if ((Integer) rs.getInt("person_id") == null) {
            book.setPerson_id(0);
        } else {
            book.setPerson_id(rs.getInt("person_id"));
        }
        return book;
    }
}
