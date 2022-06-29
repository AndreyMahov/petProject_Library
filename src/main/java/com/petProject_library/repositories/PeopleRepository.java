package com.petProject_library.repositories;

import com.petProject_library.models.Book;
import com.petProject_library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByBooks(Book book);

    Optional<Person> findByFullName(String fullname);

    Optional<List<Person>> findAllByFullNameContains(String query);

}
