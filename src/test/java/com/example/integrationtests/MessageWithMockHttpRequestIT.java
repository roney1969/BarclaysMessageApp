package com.example.integrationtests;

import com.example.entities.Message;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
public class MessageWithMockHttpRequestIT {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGettingAllMessages() throws Exception {

        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/messages")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(EXPECTED_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();

        Message[] messages = mapper.readValue(contentAsJson, Message[].class);

        assertEquals("First test message", messages[0].getContent());
        assertEquals("Second test message", messages[1].getContent());
        assertEquals("Third test message", messages[2].getContent());
        assertEquals("Fourth test message", messages[3].getContent());
    }

    @Test
    public void testFindMessagesBySenderEmail() throws Exception {
        String senderEmail = "bill@iscooler.com";
        MvcResult result =
                this.mockMvc.perform(MockMvcRequestBuilders.get("/messages/sender/email/" + senderEmail))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();
        String contentAsJson = result.getResponse().getContentAsString();

        Message[] messages = mapper.readValue(contentAsJson, Message[].class);

        assertEquals(3, messages.length);

        assertEquals("First test message", messages[0].getContent());
        assertEquals(1000, messages[0].getId());
        assertEquals("Second test message", messages[1].getContent());
        assertEquals("Third test message", messages[2].getContent());
    }

}

