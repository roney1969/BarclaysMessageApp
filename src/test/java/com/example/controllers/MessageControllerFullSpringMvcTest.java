package com.example.controllers;

import com.example.TestUtilities;
import com.example.entities.Message;
import com.example.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerFullSpringMvcTest {

    @MockBean
    MessageService mockMessageService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testServiceCalledFor_getAllMessages() throws Exception {
        ArrayList<Message> messages = TestUtilities.getMessageList();
        String expectedJson = mapper.writeValueAsString(messages);

        when(mockMessageService.findAll()).thenReturn(messages);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages");
        //  Browser looks like .accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");

        // We can do checks on the ResultActions object and return the results in one go
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedJson))     // resultActions.andExpect(content().string("[{\"id\":null,\"content\":\"message1\"},{\"id\":null,\"content\":\"message2\"}]"));
                .andReturn();

        // We can also create an MvcResult object to pick apart
//        String contentAsString = result.getResponse().getContentAsString();
//        Message[] messageArray = mapper.readValue(contentAsString, Message[].class);
//        assertEquals(messages.size(), messageArray.length);

        verify(mockMessageService, times(1)).findAll();
    }

    @Test
    void testGetMessageById() throws Exception {
        long messageId = 1L;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/" + Long.toString(messageId));
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        verify(mockMessageService, times(1)).getMessageById(messageId);
    }

    @Test
    void testGetMessageByIdBadIndex() throws Exception {
        long messageId = 0;

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/messages/" + messageId);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }


}