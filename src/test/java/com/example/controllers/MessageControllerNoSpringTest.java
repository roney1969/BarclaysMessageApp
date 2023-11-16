package com.example.controllers;

import com.example.services.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        long messageId = 1L;

        try {
            this.messageController.getMessageById(messageId);
        } catch (ResponseStatusException rse) {
            // We shouldn't have  a record there, it doesn't matter, we're testing behavior below
            System.out.println("Expected exception thrown;");
        }

        verify(this.mockMessageService, times(1)).getMessageById(messageId);
    }

    @Test
    void testGetMessageByIdBadRequest() {
        when(this.mockMessageService.getMessageById(0)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            this.messageController.getMessageById(0);
        });
    }

}