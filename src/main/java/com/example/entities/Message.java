package com.example.entities;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class Message {

    @Id
    @GeneratedValue(generator = "message_sequence")
    @SequenceGenerator(name="message_sequence", initialValue = 1)
    private Long id;

    @ManyToOne
    private Person sender;

    private String content;

    public Person getSender() {
        return sender;
    }

    public Message() {}
    public Message(String content) {this.content = content;}
    public Message(String content, Person sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {return id;}
}
