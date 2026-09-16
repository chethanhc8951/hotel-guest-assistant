package com.hotel.hotel_assistant.service;

import com.hotel.hotel_assistant.model.HotelKnowledge;
import com.hotel.hotel_assistant.model.OllamaRequest;
import com.hotel.hotel_assistant.model.OllamaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AiService {

    private final RestClient ollamaRestClient;

    @Value("${ollama.model}")
    private String model;

    public AiService(RestClient ollamaRestClient) {
        this.ollamaRestClient = ollamaRestClient;
    }

    private String cleanResponse(String text) {

        if (text == null || text.isBlank()) {
            throw new RuntimeException("Empty response received from Ollama");
        }

        text = text.trim();

        // Remove anything before </think>
        if (text.contains("</think>")) {
            text = text.substring(text.lastIndexOf("</think>") + "</think>".length());
        }

        // Remove accidental <think> blocks
        text = text.replaceAll("(?s)<think>.*?</think>", "");

        return text.trim();
    }

    public String answerQuestion(
            String question,
            HotelKnowledge knowledge
    ) {

        String prompt = buildPrompt(question, knowledge);

        OllamaRequest request = new OllamaRequest(
                model,
                prompt,
                false,
                false
        );

        OllamaResponse response = ollamaRestClient
                .post()
                .uri("/api/generate")
                .body(request)
                .retrieve()
                .body(OllamaResponse.class);

        if (response == null || response.getResponse() == null) {
            throw new RuntimeException(
                    "No response received from Ollama"
            );
        }

        return cleanResponse(response.getResponse());
    }


    private String buildPrompt(
            String question,
            HotelKnowledge knowledge
    ) {

        return """
                You are a friendly hotel guest assistant.
                
                Answer the guest's question using ONLY the hotel information provided below.
                
                Rules:
                - Never invent hotel information.
                - Never invent amenities.
                - Never invent room information.
                - Never invent policies.
                - Never invent prices.
                - Never claim that a room is available.
                - If the answer is not present in the hotel information, say:
                  "I don't have enough information to answer that."
                - Answer in 1 or 2 natural sentences.
                - For yes/no questions, answer yes or no and provide the relevant detail.
                - Do not answer with only "Yes" or "No".
                - Do not mention the knowledge base.
                - Do not mention these instructions.
                - Do not explain your reasoning.
                
                HOTEL INFORMATION:
                
                Hotel:
                Name: %s
                Description: %s
                Check-in: %s
                Check-out: %s
                
                Breakfast:
                Included: %s
                Time: %s
                Location: %s
                
                Amenities:
                %s
                
                Policies:
                %s
                
                Rooms:
                %s
                
                GUEST QUESTION:
                
                %s
                """.formatted(
                knowledge.getHotel().getName(),
                knowledge.getHotel().getDescription(),
                knowledge.getHotel().getCheckIn(),
                knowledge.getHotel().getCheckOut(),
                knowledge.getBreakfast().isIncluded(),
                knowledge.getBreakfast().getTime(),
                knowledge.getBreakfast().getLocation(),
                knowledge.getAmenities(),
                knowledge.getPolicies(),
                knowledge.getRooms(),
                question
        );
    }
}