package com.hotel.hotel_assistant.service;

import com.hotel.hotel_assistant.model.HotelKnowledge;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Getter
@Service
public class HotelKnowledgeService {

    private final HotelKnowledge hotelKnowledge;

    public HotelKnowledgeService(ObjectMapper objectMapper) throws IOException {

        ClassPathResource resource =
                new ClassPathResource("hotel-knowledge.json");

        this.hotelKnowledge =
                objectMapper.readValue(
                        resource.getInputStream(),
                        HotelKnowledge.class
                );
    }

}