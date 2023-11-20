package com.example;

import com.example.entities.Message;

import java.util.ArrayList;
import java.util.stream.StreamSupport;

public class TestUtilities {
    public static ArrayList<Message> getMessageList() {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add( new Message("message1"));
        messages.add( new Message("message2"));
        return messages;
    }

    @SuppressWarnings("unused")
    public static <T> long getSize(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).count();
    }
}
