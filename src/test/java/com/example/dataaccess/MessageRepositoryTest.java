package com.example.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageRepositoryTest {
    @Autowired
    PersonRepository personRepository;


}