package com.hotel.hotel_assistant.service;

import com.hotel.hotel_assistant.model.ConversationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversationService {

    private final Map<String, ConversationContext> conversations =
            new ConcurrentHashMap<>();

    public ConversationContext getContext(String conversationId) {

        return conversations.computeIfAbsent(
                conversationId,
                id -> new ConversationContext()
        );
    }
}