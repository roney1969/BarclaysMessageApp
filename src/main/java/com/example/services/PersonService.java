package com.example.services;

import com.example.dataaccess.PersonRepository;
import com.example.entities.Person;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person addPerson(Person person) {

        if(person.getId() != null && person.getId() != 0)
            throw  new IllegalArgumentException("The id provided for a create/post must be null or zero.");

        return this.personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
