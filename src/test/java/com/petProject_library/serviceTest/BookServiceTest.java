package com.petProject_library.serviceTest;


import com.petProject_library.models.Book;
import com.petProject_library.models.Person;
import com.petProject_library.repositories.BooksRepository;
import com.petProject_library.repositories.PeopleRepository;
import com.petProject_library.services.BooksService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static int BOOK_ID = 111;
    private static final int PERSON_ID = 111;
    private static Book testBook1;
    private static List<Book> books;


    @Mock
    private BooksRepository booksRepoMock;
    @Mock
    private PeopleRepository peopleRepoMock;

    @InjectMocks
    private BooksService service;


    @BeforeAll
    public static void unit() {

        Person testPerson = new Person();
        testPerson.setId(PERSON_ID);
        testPerson.setFullName("test");

        testBook1 = new Book();
        testBook1.setId(BOOK_ID);
        testBook1.setAuthor("author");
        testBook1.setTitle("title");
        testBook1.setYear(1999);
        testBook1.setHoldDate(new Date());
        testBook1.setOwner(testPerson);
        testPerson.setBooks(books);


        Book testBook2 = new Book();
        testBook1.setId(++BOOK_ID);
        testBook1.setAuthor("author 2");
        testBook1.setTitle("title 2");
        testBook1.setYear(2000);
        testBook1.setOwner(null);
        testBook1.setHoldDate(new Date());

        List<Book> books = List.of(testBook1, testBook2);


    }

    @Test
    public void findAll_shouldReturnAllExistBooks() {
        when(booksRepoMock.findAll()).thenReturn(books);
        Iterable<Book> actual = service.findAll();
        Iterable<Book> expected = books;
        assertIterableEquals(actual, expected);
        verify(booksRepoMock, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnExistBookWithRequestedId() {
        when(booksRepoMock.findById(any())).thenReturn(Optional.of(testBook1));
        Optional<Book> actual = service.findById(testBook1.getId());
        Optional<Book> expected = Optional.of(testBook1);
        assertEquals(actual, expected);
        verify(booksRepoMock, times(1)).findById(testBook1.getId());
    }


}
