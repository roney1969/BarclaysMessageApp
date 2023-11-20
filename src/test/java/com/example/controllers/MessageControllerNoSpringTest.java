package com.example.controllers;

import com.example.entities.Message;
import com.example.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MessageControllerNoSpringTest {

    MessageService mockMessageService;
    MessageController messageController;

    @BeforeEach
    void beforeEach() {
        this.mockMessageService = mock(MessageService.class);
        this.messageController = new MessageController(this.mockMessageService);
    }

    @Test
    void getAllMessages() {
        this.messageController.getAllMessages();
        verify(this.mockMessageService, times(1)).findAll();
    }

    @Test
    void testGetMessageById()  {
        long messageId = 1;
        // if our service returns null, the controller method will explode with a ResponseStatusException
        when(this.mockMessageService.getMessageById(messageId)).thenReturn(new Message("howdy, partner"));
        this.messageController.getMessageById(messageId);

        verify(this.mockMessageService, times(1)).getMessageById(messageId);
    }

    @Test
    void testGetMessageByIdBadRequest() {
        when(this.mockMessageService.getMessageById(0)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> this.messageController.getMessageById(0L));
    }

}