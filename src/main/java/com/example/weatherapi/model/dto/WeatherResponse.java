package com.example.weatherapi.model.dto;

public record WeatherResponse (
        String air_temperature,
        String wind_speed,
        String atmosphere_pressure,
        String humidity,
        String weather_condition,
        String location
) {
}
