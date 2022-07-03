package com.mahov.mvc.controllers;

import com.mahov.mvc.models.Person;
import com.mahov.mvc.services.PeopleService;
import com.mahov.mvc.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

   @Autowired
    public AuthController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;

   }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person")  Person person){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String PerformRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
       personValidator.validate(person,bindingResult);

       if (bindingResult.hasErrors()){
           return "auth/registration";
       }

        peopleService.create(person);
        return "redirect:/auth/login";
    }

}
