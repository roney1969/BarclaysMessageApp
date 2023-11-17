package com.example.controllers;

import com.example.entities.Message;
import com.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public Iterable<Message> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable Long messageId) {
        Message  message = messageService.getMessageById(messageId);
        if(message == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");

        return message;
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @GetMapping("/teapot")
    public Object teaPot() {
        return null;
    }
}
