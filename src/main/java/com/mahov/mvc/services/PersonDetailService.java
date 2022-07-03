package com.mahov.mvc.services;

import com.mahov.mvc.models.Person;
import com.mahov.mvc.repositories.PeopleRepository;
import com.mahov.mvc.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByLogin(username);
         if (person.isEmpty())
             throw new UsernameNotFoundException("User not found");
         return new PersonDetails(person.get());
    }

}
