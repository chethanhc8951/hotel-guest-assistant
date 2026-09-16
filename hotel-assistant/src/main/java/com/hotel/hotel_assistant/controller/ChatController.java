package com.hotel.hotel_assistant.controller;

import com.hotel.hotel_assistant.dto.ChatRequest;
import com.hotel.hotel_assistant.dto.ChatResponse;
import com.hotel.hotel_assistant.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @Valid @RequestBody ChatRequest request) {

        ChatResponse response =
                chatService.processMessage(request);

        return ResponseEntity.ok(response);
    }
}