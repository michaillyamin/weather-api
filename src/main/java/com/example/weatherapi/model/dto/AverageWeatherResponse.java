package com.example.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AverageWeatherResponse(

        @JsonProperty("average_temperature")
        String averageTemperature,

        @JsonProperty("average_wind_speed")
        String averageWindSpeed,

        @JsonProperty("average_atmosphere_pressure")
        String averageAtmospherePressure,

        @JsonProperty("average_humidity")
        String averageHumidity
) {
}
