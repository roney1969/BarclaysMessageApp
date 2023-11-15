package com.example.services;

import com.example.TestUtilities;
import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class MessageServiceSomeSpringTest {

    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepository mockRepo;

    @Test
    void findAllWithSpringDi() {
        List<Message> messages = TestUtilities.getMessageList();
        when(this.mockRepo.findAll()).thenReturn(messages);
        List<Message> actualMessages = this.messageService.findAll();

        Assertions.assertEquals(messages, actualMessages);

    }
}