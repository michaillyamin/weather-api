package com.example.weatherapi.model.exception;

public class CannotCalculateAverageWeatherDataException extends RuntimeException {
    public CannotCalculateAverageWeatherDataException(String message) {
        super(message);
    }
}
