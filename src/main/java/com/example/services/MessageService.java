package com.example.services;

import com.example.entities.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {

    public Iterable<Message> findAll() {
        return new ArrayList<>();
    }
}