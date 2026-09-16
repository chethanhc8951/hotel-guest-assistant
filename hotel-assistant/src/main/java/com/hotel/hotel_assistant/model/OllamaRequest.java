package com.hotel.hotel_assistant.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OllamaRequest {

    private String model;
    private String prompt;
    private boolean stream;
    private boolean think;

    public OllamaRequest() {
    }

    public OllamaRequest(String model, String prompt, boolean stream, boolean think) {
        this.model = model;
        this.prompt = prompt;
        this.stream = stream;
        this.think = think;
    }

}