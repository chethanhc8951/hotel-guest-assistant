package com.hotel.hotel_assistant.controller;

import com.hotel.hotel_assistant.model.HotelKnowledge;
import com.hotel.hotel_assistant.service.HotelKnowledgeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelKnowledgeService hotelKnowledgeService;

    public HotelController(HotelKnowledgeService hotelKnowledgeService) {
        this.hotelKnowledgeService = hotelKnowledgeService;
    }

    @GetMapping("/knowledge")
    public HotelKnowledge getKnowledge() {
        return hotelKnowledgeService.getHotelKnowledge();
    }
}