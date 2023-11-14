package com.example.dataaccess;

import com.example.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Populator {

    MessageRepository repo;

    @Autowired
    public Populator(MessageRepository repo) {
        this.repo = repo;
    }
    public void populate() {
        Message message = new Message("This is a message");
        this.repo.save(message);
        message = new Message("I love Java!!! (Coffee, not the language)");
        this.repo.save(message);
    }
}
