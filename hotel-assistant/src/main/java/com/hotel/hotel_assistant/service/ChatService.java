package com.hotel.hotel_assistant.service;

import com.hotel.hotel_assistant.dto.ChatRequest;
import com.hotel.hotel_assistant.dto.ChatResponse;
import com.hotel.hotel_assistant.model.ConversationContext;
import com.hotel.hotel_assistant.model.HotelKnowledge;
import com.hotel.hotel_assistant.model.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {


    private final HotelKnowledgeService hotelKnowledgeService;
    private final AvailabilityService availabilityService;


    private final AiService aiService;

    private final ConversationService conversationService;


    private boolean isAvailabilityQuestion(String question) {

        return question.contains("availability")
                || question.contains("available")
                || question.contains("book a room")
                || question.contains("booking")
                || question.contains("reserve a room")
                || question.contains("reservation");
    }



    public ChatService(
            HotelKnowledgeService hotelKnowledgeService,
            AvailabilityService availabilityService,
            AiService aiService,
            ConversationService conversationService
    ) {
        this.hotelKnowledgeService = hotelKnowledgeService;
        this.availabilityService = availabilityService;
        this.aiService = aiService;
        this.conversationService = conversationService;
    }

    public ChatResponse processMessage(ChatRequest request) {

        String conversationId = request.getConversationId();


        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }

        ConversationContext context =
                conversationService.getContext(conversationId);

        String question = request.getMessage().toLowerCase();

        /*
         * ------------------------------------
         * AVAILABILITY REQUEST
         * ------------------------------------
         */

        if (isAvailabilityQuestion(question)) {

            return handleAvailability(request, conversationId, context);
        }

        /*
         * ------------------------------------
         * GENERAL HOTEL QUESTIONS
         * ------------------------------------
         */

        HotelKnowledge knowledge =
                hotelKnowledgeService.getHotelKnowledge();

        String answer;

        try {

            answer = aiService.answerQuestion(
                    request.getMessage(),
                    knowledge
            );

        } catch (Exception e) {

            answer = "I'm sorry, I'm temporarily unable to answer "
                    + "your question. Please try again later.";
        }

        return new ChatResponse(
                conversationId,
                answer,
                "GENERAL",
                null
        );
    }

    private ChatResponse handleAvailability(
            ChatRequest request,
            String conversationId,
            ConversationContext context
    ){

        LocalDate checkIn = request.getCheckIn();
        LocalDate checkOut = request.getCheckOut();
        Integer adults = request.getAdults();

        if (checkIn != null) {
            context.setCheckIn(checkIn);
        }

        if (checkOut != null) {
            context.setCheckOut(checkOut);
        }

        if (adults != null) {
            context.setAdults(adults);
        }

        checkIn = context.getCheckIn();
        checkOut = context.getCheckOut();
        adults = context.getAdults();

        /*
         * Missing information
         */
        if (checkIn == null || checkOut == null || adults == null) {

            context.setAvailabilityInProgress(true);

            return new ChatResponse(
                    conversationId,
                    "Please provide your check-in date, "
                            + "check-out date, and number of adults.",
                    "AVAILABILITY_MISSING_INFORMATION",
                    null
            );
        }

        /*
         * Invalid number of adults
         */
        if (adults <= 0) {

            context.setAvailabilityInProgress(false);

            return new ChatResponse(
                    conversationId,
                    "Number of adults must be at least 1.",
                    "AVAILABILITY_ERROR",
                    null
            );
        }

        /*
         * Invalid dates
         */
        if (!checkOut.isAfter(checkIn)) {

            context.setAvailabilityInProgress(false);

            return new ChatResponse(
                    conversationId,
                    "Check-out date must be after the check-in date.",
                    "AVAILABILITY_ERROR",
                    null
            );
        }

        /*
         * Call deterministic availability logic
         */
        List<Room> availableRooms =
                availabilityService.checkAvailability(
                        checkIn,
                        checkOut,
                        adults
                );

        /*
         * No rooms
         */
        if (availableRooms.isEmpty()) {

            context.setAvailabilityInProgress(false);

            return new ChatResponse(
                    conversationId,
                    "Sorry, I couldn't find a suitable room "
                            + "for the requested number of guests.",
                    "AVAILABILITY",
                    List.of()
            );
        }

        context.setAvailabilityInProgress(false);

        /*
         * Rooms available
         */
        return new ChatResponse(
                conversationId,
                "I found "
                        + availableRooms.size()
                        + " suitable room(s) for your stay.",
                "AVAILABILITY",
                availableRooms
        );
    }
}