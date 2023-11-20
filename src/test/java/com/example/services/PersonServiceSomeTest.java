package com.example.services;

import com.example.TestConstants;
import com.example.dataaccess.PersonRepository;
import com.example.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class PersonServiceSomeTest {
    @Autowired
    PersonService personService;

    @MockBean
    PersonRepository mockPersonRepository;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        reset(this.mockPersonRepository);
    }

    @Test
    void addPersonHappyPath() {
        when(mockPersonRepository.save(TestConstants.BILL)).thenReturn(TestConstants.BILL);
        when(mockPersonRepository.existsById(null)).thenThrow(new InvalidDataAccessApiUsageException("The given id must not be null"));

        Person actual = this.personService.addPerson(TestConstants.BILL);
        verify(mockPersonRepository, times(1)).save(TestConstants.BILL);
    }

    @Test
    void addPersonIdExists() throws JsonProcessingException {
        String json = "{\"id\":5,\"name\":\"Bill\",\"email\":\"bill@gmail.com\"}";
        Person personWithIdNumber = mapper.readValue(json, Person.class);

        assertThrows(IllegalArgumentException.class,
                () -> {
                    this.personService.addPerson(personWithIdNumber);
                });
    }

    @Test
    void addPersonIdZero() throws JsonProcessingException {
        String json = "{\"id\":0,\"name\":\"Bill\",\"email\":\"bill@gmail.com\"}";
        Person personWithIdNumber = mapper.readValue(json, Person.class);

        this.personService.addPerson(personWithIdNumber);

        verify(mockPersonRepository, times(1)).save(any());
    }
}