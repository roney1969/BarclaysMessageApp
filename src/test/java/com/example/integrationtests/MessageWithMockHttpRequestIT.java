package com.example.integrationtests;

import com.example.controllers.MessageController;
import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Note: test class name regex '[A-Z][A-Za-z\d]*Test(s|Case)?|Test[A-Z][A-Za-z\d]*|IT(.*)|(.*)IT(Case)?'
//Note: class names nouns, method names verbs, collections plural
@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class MessageWithMockHttpRequestIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGettingAllMessages() throws Exception {

        final String expectedJson = """
[{"id":1000,"content":"First test message"},{"id":2000,"content":"Second test message"},{"id":3000,"content":"Third test message"},{"id":4000,"content":"Fourth test message"}]""";
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(content().json(expectedJson))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Message[] actualMessages = mapper.readValue(contentAsJson, Message[].class);

        assertEquals("First test message", actualMessages[0].getContent());
        assertEquals("Second test message", actualMessages[1].getContent());
        assertEquals("Third test message", actualMessages[2].getContent());
        assertEquals("Fourth test message", actualMessages[3].getContent());
    }

}

