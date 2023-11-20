package com.example.services;

import com.example.dataaccess.MessageRepository;
import com.example.dataaccess.PersonRepository;
import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    public static final String BAD_SENDER_ID = "The sender id provided for a create/post must be null or zero or an existing user..";
    MessageRepository messageRepository;
    PersonRepository personRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
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

    public List<Message> getMessagesBySenderName(String name) {
        return this.messageRepository.findMessagesBySenderName(name);
    }

    public Message addMessage(Message message) {
        if(message.getId() != null && message.getId() != 0)
            throw  new IllegalArgumentException("The message id provided for a create/post must be null or zero.");

        updateSender(message);
        return this.messageRepository.save(message);
    }

    void updateSender(Message message) {
        if(message.getSender().getId() != null && message.getSender().getId() != 0) {
            if (!this.personRepository.existsById(message.getSender().getId()))
                throw new IllegalArgumentException(BAD_SENDER_ID);
        }
        else
            this.personRepository.save(message.getSender());
    }
}