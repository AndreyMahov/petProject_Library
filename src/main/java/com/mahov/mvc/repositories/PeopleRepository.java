package com.mahov.mvc.repositories;

import com.mahov.mvc.models.Book;
import com.mahov.mvc.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByBooks(Book book);

    Optional<Person> findByFullName(String fullname);

    Optional<List<Person>> findAllByFullNameContains(String query);

    Optional<Person> findByLogin(String login);

}
