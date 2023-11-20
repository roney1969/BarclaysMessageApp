package com.example.services;

import com.example.dataaccess.PersonRepository;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PersonService {
    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(@RequestBody Person person) {
        throw new RuntimeException("Not implemented yet"); //TODO fix this, Future Dave
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
