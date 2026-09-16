package com.hotel.hotel_assistant.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HotelKnowledge {

    private Hotel hotel;
    private Breakfast breakfast;
    private List<String> amenities;
    private Map<String, String> policies;
    private List<Room> rooms;

    public HotelKnowledge() {
    }

}