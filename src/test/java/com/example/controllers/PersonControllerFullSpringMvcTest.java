package com.example.controllers;

import com.example.TestConstants;
import com.example.entities.Person;
import com.example.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(PersonController.class)
class PersonControllerFullSpringMvcTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService mockPersonService;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        reset(mockPersonService);
    }

    @Test
    void addPerson() throws Exception {
        Person person = new Person("Bill", "bill@gmail.com");
        String json = mapper.writeValueAsString(person);

        when(mockPersonService.addPerson(any())).thenReturn(person);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(mockPersonService, times(1)).addPerson(any(Person.class));
    }


    @Test
    void addPersonWithMissingIdInJson() throws Exception {
        String json = "{\"name\":\"Bill\",\"email\":\"bill@gmail.com\"}";

        when(mockPersonService.addPerson(any())).thenReturn(TestConstants.BILL);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(request);

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(mockPersonService, times(1)).addPerson(any(Person.class));
    }
    @Test
    void addPersonWithBadId() throws Exception {
        String json = "{\"id\":5, \"name\":\"Bill\",\"email\":\"bill@gmail.com\"}";

        when(mockPersonService.addPerson(any())).thenThrow(new IllegalArgumentException("some stuf"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(request);

        resultActions
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        verify(mockPersonService, times(1)).addPerson(any(Person.class));
    }
}