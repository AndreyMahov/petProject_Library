# first-springMVC-petProject_library
App for library manager.
In this app I use Spring framework, TomCat and JDBC template. 
Use SQL commands for start from package 'SQL'
enjoy=)

TO DO:
- Unit tests
- Integration tests
- Packaging in Docker container
- CI pipeline with following steps: build -> fmt check -> unit tests -> integration tests -> publish docker image
- Search users by name and books by title

functional:

1)Pages for adding, changing and deleting a person.

2)Pages for adding, editing, and deleting a book

3)A page with attractive to all people (people are clickable - when clicked, a transition to the person's page took place).

4)A page with a complex book (books are clickable - when clicked, a transition to the page of the book occurred).

5)A person's page showing their field values ​​and the list of books they have taken. If the person hasn't picked up any books, the list will instead read "no book".

6)A page of a book showing the values ​​of the margins of that book and the name of the person who borrowed that book. If this book has not been taken by anyone, the lyrics are "This book is free."

7)On the book page, if the book is taken from a person, there is a "Release Book" button next to their name. This button presses the library when the reader returns this book in exchange. After approaching this point, the book becomes free again and is sold from the person's book list.

8)On the book page, if the book is free, there is a drop-down list with all people and a button "Assign book". This button presses the library when the reader wants to take this book home. Once the correct setting is selected, the guard book will be selected in its list of books.

9)All fields are validated - with @Valid and Spring Validator if required.
