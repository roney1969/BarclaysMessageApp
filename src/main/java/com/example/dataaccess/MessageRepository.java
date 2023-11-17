package com.example.dataaccess;

import com.example.entities.Message;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends ListCrudRepository<Message, Long> {
    List<Message> findMessagesBySenderEmail(String email);
    List<Message> findMessagesBySenderName(String name);
}