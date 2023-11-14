package com.example.controllers;

import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MessageControllerSomeSpringTest {

    @Autowired
    MessageController messageController;

    @MockBean
    MessageService mockMessageService;

    @Test
    public void getObjectsFromContextAndTestBehavior() {
        Iterable<Message> messages = messageController.getAllMessages();

        verify(mockMessageService, times(1)).findAll();
    }
}
