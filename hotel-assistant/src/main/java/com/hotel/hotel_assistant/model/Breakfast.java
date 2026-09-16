package com.hotel.hotel_assistant.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Breakfast {

    private boolean included;
    private String time;
    private String location;

    public Breakfast() {
    }

}