package com.example.dataaccess;

import com.example.entities.Message;
import com.example.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Populator {

    MessageRepository messageRepository;
    PersonRepository personRepository;

    @Autowired
    public Populator(MessageRepository messageRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void populate() {
        Person bill = new Person("Bill", "bill@iscooler.com");
        bill = personRepository.save(bill);

        Message message = new Message("This is a message", bill);
        this.messageRepository.save(message);

        message = new Message("I love Java!!! (Coffee, not the language)", bill);
        this.messageRepository.save(message);
    }
}
