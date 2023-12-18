package com.example.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;

public record WeatherResponse (

        @JsonProperty("air_temperature")
        String airTemperature,

        @JsonProperty("wind_speed")
        String windSpeed,

        @JsonProperty("atmosphere_pressure")
        String atmospherePressure,

        @JsonProperty("humidity")
        String humidity,

        @JsonProperty("weather_condition")
        String weatherCondition,

        @JsonProperty("location")
        String location
) {
}
