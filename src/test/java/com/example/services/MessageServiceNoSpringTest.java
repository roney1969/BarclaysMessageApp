package com.example.services;

import com.example.TestUtilities;
import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class MessageServiceNoSpringTest {
    MessageRepository mockRepo;
    MessageService messageService;

    @BeforeEach
    void beforeEach() {
        this.mockRepo = mock(MessageRepository.class);
        this.messageService = new MessageService(this.mockRepo);
    }

    @Test
    void findAll() {
        List<Message> messages = TestUtilities.getMessageList();
        when(this.mockRepo.findAll()).thenReturn(messages);
        List<Message> actualMessages = messageService.findAll();

        Assertions.assertEquals(messages, actualMessages);
    }

    @Test
    void testRepoCalled() {
        List<Message> actualMessages = messageService.findAll();

        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void testGetMessageByIdOptionalEmpty() {
        long messageId = 1;
        Optional<Message> optionalMessage = Optional.empty();
        when(this.mockRepo.findById(messageId)).thenReturn(optionalMessage);
        Message actual = messageService.getMessageById(messageId);
        Assertions.assertNull(actual);
    }

    @Test
    void testGetMessageByIdOptionalNotEmpty() {
        Message message = new Message("Howdy");

        when(this.mockRepo.findById(any())).thenReturn(Optional.of(message));

        Message actual = messageService.getMessageById(1L);

        verify(mockRepo, times(1)).findById(any());
        Assertions.assertEquals(message.getContent(), actual.getContent());
        Assertions.assertEquals(message.getId(), actual.getId());
    }
}