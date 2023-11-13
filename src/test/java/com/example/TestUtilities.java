package com.example;

import com.example.entities.Message;

import java.util.ArrayList;

public class TestUtilities {
    public static ArrayList<Message> getMessageList() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add( new Message("message1"));
        messages.add( new Message("message2"));
        return messages;
    }
}
