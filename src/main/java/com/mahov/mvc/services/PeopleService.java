package com.mahov.mvc.services;

import com.mahov.mvc.models.Person;
import com.mahov.mvc.repositories.PeopleRepository;
import com.mahov.mvc.models.Book;
import com.mahov.mvc.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//TODO write automatic pagination
@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Test is complete
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    //TODO write in functional style. Test is no complete
    public List<Person> findAll(Optional<Integer> page, Optional<Integer> booksPerPage, Optional<String> sortBy) {

        if (page.isPresent() && booksPerPage.isPresent() && sortBy.isPresent()) {
            return peopleRepository.findAll(PageRequest.of(page.get(), booksPerPage.get(), Sort.by(sortBy.get()))).getContent();
        } else if (page.isPresent() && booksPerPage.isPresent()) {
            return peopleRepository.findAll(PageRequest.of(page.get(), booksPerPage.get())).getContent();
        } else return sortBy.map(s -> peopleRepository.findAll(Sort.by(s))).orElseGet(this::findAll);
    }

    // Test is complete
    public Optional<Person> findOne(int id) {
        return peopleRepository.findById(id);
    }

    // TODO write in functional style. Test is no complete
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<List<Book>> showBooksInHold(int id) {

        Optional<Person> owner = peopleRepository.findById(id);
        if (owner.isPresent()) {
            Optional<List<Book>> books = booksRepository.findAllByOwner(owner.get());
            if (books.isPresent()) {
                for (Book book : books.get()) {
                    LocalDate holdLocalDate = convertToLocalDateViaMillisecond(book.getHoldDate())
                            .plusDays(10);
                    book.setHoldPeriodEnded(LocalDate.now().isAfter(holdLocalDate));
                }
                return books;
            }
        }
        return Optional.empty();
    }

    //TODO Test is no complete
    public LocalDate convertToLocalDateViaMillisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // Test is complete
    public Optional<Person> findByFullname(String name) {
        return peopleRepository.findByFullName(name);
    }


    //TODO Test is no complete
    public Optional<Person> findByLogin(String login) {
        return peopleRepository.findByLogin(login);
    }

    //Test is complete
    @Transactional
    public void create(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }

    //TODO Test is no complete
    @Transactional
    public void edit(int id, Person updatePerson) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    //TODO Test is no complete
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    //TODO Test is no complete

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Optional<List<Person>> findAllByFullname(String query) {
        return peopleRepository.findAllByFullNameContains(query);
    }



}
