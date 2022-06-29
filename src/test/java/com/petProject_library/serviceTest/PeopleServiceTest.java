package com.petProject_library.serviceTest;

import com.petProject_library.models.Book;
import com.petProject_library.models.Person;
import com.petProject_library.repositories.BooksRepository;
import com.petProject_library.repositories.PeopleRepository;
import com.petProject_library.services.PeopleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {
    private static int PERSON_ID = 111;
    private static Person testPerson;
    private static List<Person> people;

    @Mock
    private PeopleRepository peopleRepoMock;
    @Mock
    private BooksRepository booksRepoMock;
    @InjectMocks
    private PeopleService service;

    @BeforeAll
    public static void init() {
        // инициализируем какие-то данные, которые пригодятся для ВСЕХ тестов
        testPerson = new Person();
        testPerson.setId(PERSON_ID);
        testPerson.setFullName("test");

        Book testBook1 = new Book();
        testBook1.setHoldDate(new Date());

        Book testBook2 = new Book();
        testBook2.setHoldDate(new Date());

        List<Book> books = List.of(testBook1, testBook2);
        testPerson.setBooks(books);

        Person testPerson2 = new Person();
        testPerson2.setId(++PERSON_ID);

        Person testPerson3 = new Person();
        testPerson3.setId(++PERSON_ID);

        people = asList(testPerson, testPerson2, testPerson3);
    }

    @Test
    public void findById_ShouldReturnExistPersonWithRequestedId() {
        // задаем ожидаемое поведение
        when(peopleRepoMock.findById(any())).thenReturn(Optional.of(testPerson));

        // вызываем тестируемый метод
        Optional<Person> actual = service.findOne(testPerson.getId());

        // делаем проверку, что actual == expected
        assertEquals(actual, Optional.of(testPerson));

        //верифицируем, что наш мок был вызван столько раз и с таким аргументом как мы и ожидали
        verify(peopleRepoMock, times(1)).findById(testPerson.getId());

    }

    @Test
    public void findAll_ShouldReturnListOfExistPeople() {
        when(peopleRepoMock.findAll()).thenReturn(people);

        List<Person> actual = service.findAll();

        assertArrayEquals(actual.toArray(), people.toArray());

        verify(peopleRepoMock, times(1)).findAll();
    }

    @Test
    public void findByFullname_shouldReturnExistPersonWithRequestedFullname() {
        when(peopleRepoMock.findByFullName(any())).thenReturn(Optional.of(testPerson));

        Optional<Person> actual = peopleRepoMock.findByFullName(testPerson.getFullName());

        assertEquals(actual, Optional.of(testPerson));

        verify(peopleRepoMock, times(1)).findByFullName(testPerson.getFullName());
    }

    @Test
    public void create_shouldCreateNewPersonAndPutInDB() {
        when(peopleRepoMock.save(any())).thenReturn(testPerson);

        Optional<Person> actual = Optional.of(peopleRepoMock.save(testPerson));

        assertEquals(actual, Optional.of(testPerson));

        verify(peopleRepoMock, times(1)).save(testPerson);
    }

}