package com.example.weatherapi.controller;

import com.example.weatherapi.model.dto.AverageWeatherRequest;
import com.example.weatherapi.model.dto.AverageWeatherResponse;
import com.example.weatherapi.model.dto.WeatherResponse;
import com.example.weatherapi.service.impl.WeatherServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherServiceImpl weatherService;

    public WeatherController(WeatherServiceImpl weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping()
    public ResponseEntity<WeatherResponse> getLatestWeatherData() {
        WeatherResponse weatherResponse = weatherService.findLatestData();
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }

    @GetMapping("/average")
    public ResponseEntity<AverageWeatherResponse> getAverageWeatherData(@RequestBody AverageWeatherRequest averageWeatherRequest) {
        AverageWeatherResponse weatherResponse = weatherService.findAverageWeatherData(averageWeatherRequest);
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }
}
