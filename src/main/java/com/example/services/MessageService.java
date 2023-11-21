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

    public static final String SENDER_MUST_EXIST = "The sender of the message must already exist.";
    public static final String MESSAGE_ID_MUST_BE_NULL_OR_0 = "The message id provided for a create/post must be null or zero.";
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
        // If the user provided an ID for the message
        if(message.getId() != null && message.getId() != 0)
            throw new IllegalArgumentException(MESSAGE_ID_MUST_BE_NULL_OR_0);

        Person sender = message.getSender();

        // If the sender ID isn't set to a valid number
        if(sender.getId() == null)
            throw new IllegalArgumentException(SENDER_MUST_EXIST);

        // If the user provided a valid integer for ID, but it's not in the database
        if(!this.personRepository.existsById(sender.getId()))
            throw new IllegalArgumentException(SENDER_MUST_EXIST);

        // If we've gotten here, it's safe to attempt to save the message
        return this.messageRepository.save(message);
    }
}