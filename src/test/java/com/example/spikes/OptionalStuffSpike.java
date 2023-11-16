package com.example.spikes;

import com.example.entities.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

public class OptionalStuffSpike {

    @Test
    public void testEmptyCollectionMetaphor() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Message message = null;
        String json = objectMapper.writeValueAsString(message);
        System.out.println("JSON: " + json);

        Optional<Message> optionalMessage = Optional.empty();
        json = objectMapper.writeValueAsString(optionalMessage);
        System.out.println("JSON: " + json);

    }
}
