package ru.mahov.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    Optional<List<Book>> findAllByOwner(Person owner);

}
