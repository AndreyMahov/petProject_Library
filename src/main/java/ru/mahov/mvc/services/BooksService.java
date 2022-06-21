package ru.mahov.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;
import ru.mahov.mvc.repositories.BooksRepository;
import ru.mahov.mvc.repositories.PeopleRepository;

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

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
    }

    public Optional<Person> findOwner(int id) {
        return peopleRepository.findByBooks(booksRepository.findById(id).get());
    }

    @Transactional
    public void create(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void edit(int id, Book updateBook) {
        updateBook.setId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void hold(int bookId, int ownerId) {
        Book holdBook = booksRepository.findById(bookId).get();
        Person owner = peopleRepository.findById(ownerId).get();
        holdBook.setOwner(owner);
        owner.getBooks().add(holdBook);
        peopleRepository.save(owner);
        booksRepository.save(holdBook);
    }

    @Transactional
    public void release(int bookId) {
        Book releaseBook = booksRepository.findById(bookId).get();
        Person owner = peopleRepository.findByBooks(releaseBook).get();
        releaseBook.setOwner(null);
        owner.getBooks().remove(releaseBook);
        peopleRepository.save(owner);
        booksRepository.save(releaseBook);
        System.out.println("test");
    }

}
