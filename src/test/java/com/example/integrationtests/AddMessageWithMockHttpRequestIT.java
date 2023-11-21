package com.example.integrationtests;

import com.example.dataaccess.PersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Note: test class name regex '[A-Z][A-Za-z\d]*Test(s|Case)?|Test[A-Z][A-Za-z\d]*|IT(.*)|(.*)IT(Case)?'
//Note: class names nouns, method names verbs, collections plural
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class AddMessageWithMockHttpRequestIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Person existingPerson;
    Person newPerson;

    @BeforeEach
    void beforeEach() {
        if(existingPerson == null)
            existingPerson = new Person("Existing Person", "iexist@universe.com");

        // Ensure this Person object has an ID
        if(existingPerson.getId() == null)
            this.personRepository.save(existingPerson);

        Assertions.assertNotNull(this.existingPerson.getId());

        // Will not have an ID
        newPerson = new Person("New Person", "noob@noob.com");
        Assertions.assertNull(newPerson.getId());
    }

    static final String NEW_MESSAGE_CONTENT = "This is the content of a new message";

    @Test
    void testAddMessageHappyPath() throws Exception {
        Message newMessage = new Message(NEW_MESSAGE_CONTENT, this.existingPerson);
        String json = this.objectMapper.writeValueAsString(newMessage);

        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        checkMessageFields(result, newMessage);
    }

    @Test
    void testAddMessageIllegalMessageId() throws Exception {
        Message newMessage = new Message(NEW_MESSAGE_CONTENT, this.existingPerson);
        String json = getMessageJson(newMessage, 5L, newMessage.getSender().getId());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testAddMessageIllegalSenderId() throws Exception {
        Message newMessage = new Message(NEW_MESSAGE_CONTENT, newPerson);
        assertNull(newPerson.getId());  // Sanity check

        String json = getMessageJson(newMessage, newMessage.getId(), newMessage.getSender().getId());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testAddMessageWithNoSenderId() throws Exception {
        String json =
                """
                {
                  "id": %d,
                  "sender": {
                    "name": "%s",
                    "email": "%s"
                  },
                  "content": "Josh's first message."
                }""".formatted(0, existingPerson.getName(), existingPerson.getEmail());

        Message expectedMessage = this.objectMapper.readValue(json, Message.class);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testAddMessageHappyPathWithNoMessageId() throws Exception {
        String json =
                """
                {
                  "sender": {
                    "id": %d,
                    "name": "%s",
                    "email": "%s"
                  },
                  "content": "Josh's first message."
                }""".formatted(existingPerson.getId(), existingPerson.getName(), existingPerson.getEmail());

        Message expectedMessage = this.objectMapper.readValue(json, Message.class);

        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        checkMessageFields(result, expectedMessage);
    }

    private void checkMessageFields(MvcResult result, Message expectedMessage) throws UnsupportedEncodingException, JsonProcessingException {
        String resultJson = result.getResponse().getContentAsString();
        Message resultMessage = this.objectMapper.readValue(resultJson, Message.class);

        // Ensure data fields match expected values
        assertEquals(expectedMessage.getSender().getName(), resultMessage.getSender().getName());
        assertEquals(expectedMessage.getSender().getEmail(), resultMessage.getSender().getEmail());
        assertEquals(expectedMessage.getContent(), resultMessage.getContent());

        // Ensure IDs have been set for both entities
        Assertions.assertNotNull(resultMessage.getId());
        Assertions.assertTrue(resultMessage.getId() > 0);

        Assertions.assertNotNull(resultMessage.getSender().getId());
        Assertions.assertTrue(resultMessage.getSender().getId() > 0);
    }

    private String getMessageJson(Message message, Long messageId, Long senderId) {
        Person sender = message.getSender();
        return """
                {
                  "id": %d,
                  "sender": {
                    "id": %d,
                    "name": "%s",
                    "email": "%s"
                  },
                  "content": "%s"
                }""".formatted(messageId, senderId, sender.getName(), sender.getEmail(), message.getContent());
    }
}

