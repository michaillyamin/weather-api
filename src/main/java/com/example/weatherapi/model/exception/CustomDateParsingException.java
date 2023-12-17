package com.example.weatherapi.model.exception;

public class CustomDateParsingException extends RuntimeException {
    public CustomDateParsingException(String message) {
        super(message);
    }
}
