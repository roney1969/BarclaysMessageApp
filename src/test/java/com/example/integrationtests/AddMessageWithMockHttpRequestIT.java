package com.example.integrationtests;

import com.example.entities.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static com.example.TestConstants.EXPECTED_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Note: test class name regex '[A-Z][A-Za-z\d]*Test(s|Case)?|Test[A-Z][A-Za-z\d]*|IT(.*)|(.*)IT(Case)?'
//Note: class names nouns, method names verbs, collections plural
@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:test-data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class AddMessageWithMockHttpRequestIT {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void testAddMessageHappyPath() throws Exception {
        String json = """
                {
                  "id": 0,
                  "sender": {
                    "id": 0,
                    "name": "Josh",
                    "email": "josh@joshuatree.com"
                  },
                  "content": "Josh's first message."
                }""";
        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages"))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        checkMessageFields(result);
    }

    @Test
    void testAddMessageIllegalId() throws Exception {
        String json = """
                {
                  "id": 5,
                  "sender": {
                    "id": 0,
                    "name": "Josh",
                    "email": "josh@joshuatree.com"
                  },
                  "content": "Josh's first message."
                }""";
        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }
    @Test
    void testAddMessageIllegalSenderId() throws Exception {
        String json = """
                {
                  "id": 0,
                  "sender": {
                    "id": 5,
                    "name": "Josh",
                    "email": "josh@joshuatree.com"
                  },
                  "content": "Josh's first message."
                }""";
        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    void testAddMessageWithNoId() throws Exception {
        String json = """
                {
                  "sender": {
                    "name": "Josh",
                    "email": "josh@joshuatree.com"
                  },
                  "content": "Josh's first message."
                }""";

        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.post("/messages"))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        checkMessageFields(result);

    }

    private void checkMessageFields(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String resultJson = result.getResponse().getContentAsString();
        Message resultMessage = this.objectMapper.readValue(resultJson, Message.class);

        assertEquals("Josh", resultMessage.getSender().getName());
        assertEquals("josh@joshuatree.com", resultMessage.getSender().getEmail());
        assertEquals("Josh's first message.", resultMessage.getContent());

        Assertions.assertNotNull(resultMessage.getId());
        Assertions.assertTrue(resultMessage.getId() > 0);

        Assertions.assertNotNull(resultMessage.getSender().getId());
        Assertions.assertTrue(resultMessage.getSender().getId() > 0);
    }
}

