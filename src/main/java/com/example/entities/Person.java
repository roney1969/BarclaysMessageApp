package com.example.entities;

import com.example.entities.Message;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {return id;}

//    @OneToMany(mappedBy =  "sender")
//    private HashSet<Message> sentMessages;

    private String name;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    private String email;
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Person()  {}

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
}