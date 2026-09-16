package com.hotel.hotel_assistant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    @NotBlank(message = "Message cannot be empty")
    private String message;

    private String conversationId;

    private LocalDate checkIn;

    private LocalDate checkOut;

    @Min(value = 1, message = "Number of adults must be at least 1")
    private Integer adults;

}