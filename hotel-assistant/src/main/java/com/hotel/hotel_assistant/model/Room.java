package com.hotel.hotel_assistant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    private String name;
    private int capacity;
    private String beds;
    private double pricePerNight;

}