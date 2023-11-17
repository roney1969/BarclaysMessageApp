package com.example.controllers;

import com.example.entities.Message;
import com.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    MessageService messageService;


    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("")
    public Iterable<Message> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/{messageId}")
    public Message getMessageById(@PathVariable Long messageId) {
        Message  message = messageService.getMessageById(messageId);
        if(message == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");

        return message;
    }

    @GetMapping("/sender/email/{email}")
    public List<Message> getMessagesBySenderEmail(@PathVariable String email){
        //Note: here we're returning an empty string in two cases
        // 1. If the sender (person) has no associated methods
        // 2. If we don't even know about that sender email address (i.e. no corresponding person)
        // Is this what you want?
        return  messageService.getMessagesBySenderEmail(email);
    }

    @GetMapping("/sender/name/{name}")
    public List<Message> getMessagesBySenderName(@PathVariable String name){
        return  null;
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @GetMapping("/teapot")
    public Object teaPot() {
        return null;
    }
}
