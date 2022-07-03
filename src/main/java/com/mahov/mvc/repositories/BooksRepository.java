package com.mahov.mvc.repositories;

import com.mahov.mvc.models.Person;
import com.mahov.mvc.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    Optional<List<Book>> findAllByOwner(Person owner);

    Optional<List<Book>> findAllByTitleContains(String query);

}
