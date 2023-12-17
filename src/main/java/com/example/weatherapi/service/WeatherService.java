package com.example.weatherapi.service;

import com.example.weatherapi.model.dto.AverageWeatherRequest;
import com.example.weatherapi.model.dto.AverageWeatherResponse;
import com.example.weatherapi.model.dto.WeatherResponse;
import jakarta.validation.Valid;

public interface WeatherService {

    WeatherResponse findLatestData();

    AverageWeatherResponse findAverageWeatherData(@Valid AverageWeatherRequest averageWeatherRequest);
}
