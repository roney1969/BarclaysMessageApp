package com.example.services;

import com.example.TestUtilities;
import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageServiceNoSpringTest {

    @Test
    void findAll() {
        MessageRepository mockRepo = mock(MessageRepository.class);

        List<Message> messages = TestUtilities.getMessageList();
        when(mockRepo.findAll()).thenReturn(messages);

        MessageService messageService = new MessageService(mockRepo);

        List<Message> actualMessages = messageService.findAll();

        Assertions.assertEquals(messages, actualMessages);
    }
}