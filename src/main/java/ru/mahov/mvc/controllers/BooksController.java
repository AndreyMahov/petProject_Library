package ru.mahov.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mahov.mvc.dao.BooksDAO;
import ru.mahov.mvc.dao.PersonDAO;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BooksDAO booksDAO, PersonDAO personDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksDAO.index());
        return "/book/index";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/edit/{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "/book/edit";

        booksDAO.update(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("book") Book book) {
        return "/book/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/book/new";

        booksDAO.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksDAO.show(id));

        Optional<Person> owner = booksDAO.getOwner(id);

        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }

        return "/book/show";
    }


    @PatchMapping("/givebook/{id}")
    public String giveBook(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        booksDAO.giveBook(book.getPerson_id(), id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/takebook/{id}")
    public String takeBook(@PathVariable("id") int id) {
        booksDAO.takeBook(id);
        return "redirect:/books/" + id;
    }

}
