package com.example.spikes;

import com.example.dataaccess.MessageRepository;
import com.example.entities.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:test-data.sql")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class EntityFactoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MessageRepository repo;

    @Test
    public void testGettingEntity() {
        EntityManagerFactory factory = entityManager.getEntityManagerFactory();
        Message keyedMessage = makeKeyedMessage();
        System.out.println(keyedMessage);
    }

    Message makeKeyedMessage() {
        return repo.save(new Message());
    }
}
