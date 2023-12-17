package com.example.weatherapi.model.dto;

public record AverageWeatherResponse(
        String average_temperature,
        String average_wind_speed,
        String average_atmosphere_pressure,
        String average_humidity
) {
}
