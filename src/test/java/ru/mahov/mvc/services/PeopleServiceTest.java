package ru.mahov.mvc.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mahov.mvc.models.Person;
import ru.mahov.mvc.repositories.BooksRepository;
import ru.mahov.mvc.repositories.PeopleRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class PeopleServiceTest {
    private int PERSON_ID = 111;

    @Mock
    private PeopleRepository peopleRepoMock;

    @Mock
    private BooksRepository booksRepoMock;

    @InjectMocks
    private PeopleService service;

    private Person vova;

    @Before
    public void init() {
        // инициализируем какие-то данные, которые пригодятся для ВСЕХ тестов
        vova = new Person();
        vova.setId(PERSON_ID);
    }

    @Test
    public void findByIdAndPersonExist() {
        // задаем ожидаемое поведение
        Mockito.when(peopleRepoMock.findById(any())).thenReturn(Optional.of(vova));

        // вызываем тестируемый метод
        Optional<Person> actual = service.findOne(PERSON_ID);

        // делаем проверку, что actual == expected
        Assert.assertEquals(actual, Optional.of(vova));

        //верифицируем, что наш мок был вызван столько раз и с таким аргументом как мы и ожидали
        //fixme
        Mockito.verify(peopleRepoMock, Mockito.times(1)).findById(111);
    }
}