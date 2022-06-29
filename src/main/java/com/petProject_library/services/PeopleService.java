package com.petProject_library.services;

import com.petProject_library.models.Book;
import com.petProject_library.models.Person;
import com.petProject_library.repositories.BooksRepository;
import com.petProject_library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private PeopleRepository peopleRepository;
    private BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public PeopleService() {
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

    //Test is complete
    @Transactional
    public void create(Person person) {
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
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    //TODO Test is no complete
    public Optional<List<Person>> findAllByFullname(String query) {
        return peopleRepository.findAllByFullNameContains(query);
    }

}
