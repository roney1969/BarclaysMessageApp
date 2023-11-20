package com.example.integrationtests;

import com.example.controllers.PersonController;
import com.example.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(PersonController.class)
class PersonControllerWithMockHttpRequestIT {
    @Autowired
    PersonController personController;

    @MockBean
    PersonService mockPersonService;

    @Test
    void getAllPersons() {
    }
}