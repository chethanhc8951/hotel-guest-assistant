package com.hotel.hotel_assistant.service;

import com.hotel.hotel_assistant.model.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {

    public List<Room> checkAvailability(
            LocalDate checkIn,
            LocalDate checkOut,
            int adults
    ) {

        List<Room> rooms = List.of(

                new Room(
                        "Deluxe Room",
                        2,
                        "1 King Bed",
                        120
                ),

                new Room(
                        "Family Room",
                        4,
                        "2 Queen Beds",
                        180
                )
        );

        return rooms.stream()
                .filter(room -> room.getCapacity() >= adults)
                .toList();
    }
}