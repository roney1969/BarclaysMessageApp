package com.example.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


@SuppressWarnings("unused")
@Entity
public class Person {

    @Id
    @GeneratedValue(generator = "person_sequence")
    @SequenceGenerator(name="person_sequence", initialValue = 1)
    private Long id;
    public Long getId() {return id;}

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