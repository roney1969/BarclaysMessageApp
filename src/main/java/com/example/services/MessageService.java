package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MessageService {

    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        return this.messageRepository.findAll();
    }


    public Message getMessageById(long messageId) {
        Optional<Message> message = this.messageRepository.findById(messageId);
        return message.orElse(null);
    }

    public List<Message> getMessagesBySenderEmail(String email) {
        return messageRepository.findMessagesBySenderEmail(email);
    }
}