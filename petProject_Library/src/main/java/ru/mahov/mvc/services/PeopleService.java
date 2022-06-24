package ru.mahov.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;
import ru.mahov.mvc.repositories.BooksRepository;
import ru.mahov.mvc.repositories.PeopleRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    //TODO автоматическая пагинация
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;
//    private final int LIMIT_WITH_OUT_PAGINATION = 10;
//    private final int COUNT_PAGES = (findAll().size() % LIMIT_WITH_OUT_PAGINATION) +1;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public List<Person> findAll(Optional<Integer> page, Optional<Integer> booksPerPage, Optional<String> sortBy) {

        if (page.isPresent() && booksPerPage.isPresent() && sortBy.isPresent()) {
            return peopleRepository.findAll(PageRequest.of(page.get(), booksPerPage.get(), Sort.by(sortBy.get()))).getContent();
        } else if ((page.isPresent() && booksPerPage.isPresent()) && !sortBy.isPresent()) {
            return peopleRepository.findAll(PageRequest.of(page.get(), booksPerPage.get())).getContent();
        } else if ((!page.isPresent() || !booksPerPage.isPresent()) && sortBy.isPresent()) {
            return peopleRepository.findAll(Sort.by(sortBy.get()));
        }
//        else if (findAll().size() > LIMIT_WITH_OUT_PAGINATION){
//            return peopleRepository.findAll(PageRequest.of(6, 2)).getContent();
//        }
        else {
            return findAll();
        }
    }


    public Optional<Person> findOne(int id) {
        return peopleRepository.findById(id);
    }

    public Optional<List<Book>> showBooksInHold(int id) {

        // TODO написать в функциональном стиле
//        peopleRepository.findById(id)
//                .flatMap(booksRepository::findAllByOwner)
//                .map(books -> books.stream().map(book -> {
//                    LocalDate CalculateEndHoldDate = convertToLocalDateViaMilisecond(book.getHoldDate()).plusDays(10);
//                   book.setHoldPeriodEnded(LocalDate.now().isAfter(CalculateEndHoldDate));
//                    return books;
//                }));


//peopleRepository.findById(id).flatMap(booksRepository::findAllByOwner).map(books -> {
//    for (Book book : books){
//                   LocalDate CalculateEndHoldDate = convertToLocalDateViaMilisecond(book.getHoldDate()).plusDays(10);
//
//                    if (LocalDate.now().isAfter(CalculateEndHoldDate)){
//                        book.setHoldPeriodEnded(true);
//                    }
//                    else {
//                        book.setHoldPeriodEnded(false);
//                    }
//                }
//                return books;
//});
//return Optional.empty();

        Optional<Person> owner = peopleRepository.findById(id);
        if (owner.isPresent()) {
            Optional<List<Book>> books = booksRepository.findAllByOwner(owner.get());
            if (!books.isEmpty()) {
                for (Book book : books.get()) {
                    LocalDate test = convertToLocalDateViaMilisecond(book.getHoldDate()).plusDays(10);

                    book.setHoldPeriodEnded(LocalDate.now().isAfter(test));
                    System.out.println("test");
                }
                return books;
            }

        }
        return Optional.empty();

    }

    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Optional<Person> findByFullname(String name) {
        return peopleRepository.findByFullName(name);
    }

    @Transactional
    public void create(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void edit(int id, Person updatePerson) {
        updatePerson.setId(id);
        peopleRepository.save(updatePerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<List<Person>> findAllByFullname(String query) {
        return peopleRepository.findAllByFullNameContains(query);
    }

}
