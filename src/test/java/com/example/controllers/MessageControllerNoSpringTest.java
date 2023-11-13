package com.example.controllers;

import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MessageControllerNoSpringTest {

    @Test
    void getAllMessages() {
        MessageService mockMessageService = mock(MessageService.class);
        MessageController messageController = new MessageController(mockMessageService);

        Iterable<Message> messages = messageController.getAllMessages();

        verify(mockMessageService, times(1)).findAll();
    }
}