package com.example.entities;

import com.example.entities.Message;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy =  "sender")
    private List<Message> sentMessages;

    private String name;
    private String email;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Person()  {}

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        sentMessages = new ArrayList<>();
    }
}