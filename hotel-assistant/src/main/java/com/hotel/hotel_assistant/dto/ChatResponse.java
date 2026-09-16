package com.hotel.hotel_assistant.dto;

import com.hotel.hotel_assistant.model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private String conversationId;
    private String message;
    private String type;
    private List<Room> availability;

}