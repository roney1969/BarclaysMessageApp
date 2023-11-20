package com.example.services;

import com.example.TestUtilities;
import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
class MessageServiceSomeSpringTest {

    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepository mockRepo;

    @BeforeEach
    void beforeEach() {
        reset(mockRepo);
    }

    @Test
    void findAllWithSpringDi() {
        List<Message> messages = TestUtilities.getMessageList();
        when(this.mockRepo.findAll()).thenReturn(messages);
        List<Message> actualMessages = this.messageService.findAll();

        Assertions.assertEquals(messages, actualMessages);
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

        Assertions.assertEquals(message.getContent(), actual.getContent());
        Assertions.assertEquals(message.getId(), actual.getId());
    }
}