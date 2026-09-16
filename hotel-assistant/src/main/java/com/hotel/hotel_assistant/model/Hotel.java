package com.hotel.hotel_assistant.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hotel {

    private String name;
    private String description;
    private String checkIn;
    private String checkOut;

    public Hotel() {
    }

}