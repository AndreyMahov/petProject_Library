package com.mahov.mvc.controllers;

import com.mahov.mvc.models.Person;
import com.mahov.mvc.services.PeopleService;
import com.mahov.mvc.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Optional<Integer> page,
                        @RequestParam(value = "books_per_page", required = false) Optional<Integer> booksPerPage,
                        @RequestParam(value = "sort_by", required = false) Optional<String> sortBy) {

        model.addAttribute("people", peopleService.findAll(page, booksPerPage, sortBy));
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        peopleService.findOne(id)
                .ifPresent(person -> model.addAttribute("person", person));
        peopleService.showBooksInHold(id)
                .ifPresent(books -> model.addAttribute("books", books));
        return "/people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        peopleService.findOne(id)
                .ifPresent(person -> model.addAttribute("person", person));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        peopleService.edit(id, person);
        return "redirect:/people/" + id;
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/new";
        peopleService.create(person);
        return "redirect:/people";
    }

    @GetMapping("/search")
    public String searchByNamePage() {
        return "people/search";
    }

    @PostMapping("/search")
    public String searchByNameResult(Model model, @RequestParam("query") String query) {
        peopleService.findAllByFullname(query).ifPresent(people -> model.addAttribute("people", people));
        return "people/search";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("person") Person person, @PathVariable int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}
