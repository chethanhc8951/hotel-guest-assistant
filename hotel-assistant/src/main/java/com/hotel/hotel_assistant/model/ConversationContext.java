package com.hotel.hotel_assistant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationContext {

    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer adults;
    private boolean availabilityInProgress;



}