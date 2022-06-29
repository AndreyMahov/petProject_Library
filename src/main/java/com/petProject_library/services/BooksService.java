package com.petProject_library.services;

import com.petProject_library.models.Book;
import com.petProject_library.models.Person;
import com.petProject_library.repositories.BooksRepository;
import com.petProject_library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    //TODO Test is complete
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    //TODO Test is complete
    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
    }

    //TODO Test is no complete
    public Optional<Person> findOwner(int id) {
        return peopleRepository.findByBooks(booksRepository.findById(id).orElse(null));
    }

    //TODO Test is no complete
    @Transactional
    public void create(Book book) {
        booksRepository.save(book);
    }

    //TODO Test is no complete
    @Transactional
    public void edit(int id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    //TODO Test is no complete
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    //TODO Test is no complete
    @Transactional
    public void hold(int bookId, int ownerId) {
        booksRepository.findById(bookId).flatMap(holdBook -> peopleRepository
                .findById(ownerId)
                .map(owner -> {
                    holdBook.setOwner(owner);
                    holdBook.setHoldDate(new Date());
                    holdBook.setHoldPeriodEnded(false);
                    owner.getBooks().add(holdBook);
                    peopleRepository.save(owner);

                    return null;
                }));
    }

    //TODO Test is no complete
    @Transactional
    public void release(int bookId) {
        booksRepository
                .findById(bookId)
                .flatMap(releaseBook -> peopleRepository
                        .findByBooks(releaseBook)
                        .map(owner -> {
                            releaseBook.setOwner(null);
                            releaseBook.setHoldDate(null);
                            owner.getBooks().remove(releaseBook);
                            peopleRepository.save(owner);

                            return null;
                        }));
    }

    //TODO Test is no complete
    public Optional<List<Book>> searchByTitle(String query) {
        return booksRepository.findAllByTitleContains(query);
    }

}
