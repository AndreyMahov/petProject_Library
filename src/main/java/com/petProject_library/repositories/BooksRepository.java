package com.petProject_library.repositories;

import com.petProject_library.models.Book;
import com.petProject_library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    Optional<List<Book>> findAllByOwner(Person owner);

    Optional<List<Book>> findAllByTitleContains(String query);

}
