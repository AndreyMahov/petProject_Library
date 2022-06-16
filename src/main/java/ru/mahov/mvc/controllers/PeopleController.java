package ru.mahov.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.mahov.mvc.dao.PersonDAO;
import ru.mahov.mvc.models.Person;
import ru.mahov.mvc.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;

        this.personValidator = personValidator;
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "/people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", personDAO.showBooks(id));
        return "/people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "/people/edit";

        personDAO.update(id, person);
        return "redirect:/people/" + id;
    }


    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("person") Person person, @PathVariable int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }


}
