package ru.mahov.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mahov.mvc.models.Book;
import ru.mahov.mvc.models.Person;
import ru.mahov.mvc.services.BooksService;
import ru.mahov.mvc.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "/book/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        booksService.findById(id)
                .ifPresent(book -> model.addAttribute("book", book));
        Optional<Person> owner = booksService.findOwner(id);

        owner.ifPresent(person -> model.addAttribute("owner", person));
        if (!owner.isPresent()) {
            model.addAttribute("people", peopleService.findAll());
        }
        return "/book/show";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        booksService.findById(id).ifPresent(book -> model.addAttribute("book", book));
        return "book/edit";
    }

    @PatchMapping("/edit/{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "/book/edit";
        booksService.edit(id, book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return "/book/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/book/new";
        booksService.create(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/hold-book/{id}")
    public String holdBook(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        booksService.hold(id, book.getOwner().getId());
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "/book/search";
    }

    @PostMapping("/search")
    public String searchResult(Model model, @RequestParam("query") String query) {
        booksService.searchByTitle(query).ifPresent(books -> model.addAttribute("books", books));
        return "/book/search";
    }

    @PatchMapping("/release-book/{id}")
    public String releaseBook(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/" + id;
    }

}
