CREATE TABLE Person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    fullname varchar(100) NOT NULL UNIQUE,
    yearofbirth int NOT NULL
);

INSERT INTO person(fullname,yearOfBirth) VALUES('Игнасиас Мигель Педрович',1990)
INSERT INTO person(fullname,yearOfBirth) VALUES('Иванов Иван Иванович',2008)
INSERT INTO person(fullname,yearOfBirth) VALUES('Махов Андрей Леонидович',1994)
INSERT INTO person(fullname,yearOfBirth) VALUES('Романов Антон Игоревич',1994)
INSERT INTO person(fullname,yearOfBirth) VALUES('Куцепко Вадим Афанасьевич',1995)
INSERT INTO person(fullname,yearOfBirth) VALUES('Петров Геннадий Алексеевич',1991)


CREATE TABLE Book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(100) NOT NULL,
    author varchar(100) NOT NULL,
    year int NOT NULL,
    person_id int REFERENCES Person(id) ON DELETE SET NULL
);

INSERT INTO book(title,author,year) VALUES('Алгоритмы: построение и анализ','Томас Кормен',1989)
INSERT INTO book(title,author,year) VALUES('Java. Эффективное программирование','Джошуа Блох',2001)
INSERT INTO book(title,author,year) VALUES('Дюна','Фрэнк Герберт',1965)
INSERT INTO book(title,author,year) VALUES('Java 8','Шилдт Герберт',2014)
INSERT INTO book(title,author,year) VALUES('Философия Java','Брюс Эккель',1998)

