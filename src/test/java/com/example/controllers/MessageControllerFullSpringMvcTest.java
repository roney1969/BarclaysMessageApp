package com.example.controllers;

import com.example.TestUtilities;
import com.example.entities.Message;
import com.example.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc
class MessageControllerFullSpringMvcTest {

    @MockBean
    MessageService mockMessageService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testServiceCalledFor_getAllMessages() throws Exception {
        ArrayList<Message> messages = TestUtilities.getMessageList();

        when(mockMessageService.findAll()).thenReturn(messages);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages");
//  Browser               .accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");

        // We can do checks on the ResultActions object
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().string("[{\"id\":null,\"content\":\"message1\"},{\"id\":null,\"content\":\"message2\"}]"));

        // We can also creqte an MvcResult object to pick apart
        MvcResult result = resultActions.andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        Message[] messageArray = mapper.readValue(contentAsString, Message[].class);
        assertEquals(messages.size(), messageArray.length);

        verify(mockMessageService, times(1)).findAll();
    }


}