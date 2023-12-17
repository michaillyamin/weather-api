package com.example.weatherapi.service.impl;

import com.example.weatherapi.connector.WeatherConnect;
import com.example.weatherapi.mapper.WeatherMapper;
import com.example.weatherapi.model.dto.WeatherRequest;
import com.example.weatherapi.model.entity.Weather;
import com.example.weatherapi.repository.WeatherRepository;
import com.example.weatherapi.service.ScheduleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final WeatherRepository weatherRepository;
    private final WeatherConnect weatherConnect;
    private final WeatherMapper weatherMapper;

    @Value("${base.city}")
    private String city;

    @Value("${base.key}")
    private String key;

    @Value("${base.host}")
    private String host;

    public ScheduleServiceImpl(WeatherRepository weatherRepository, WeatherConnect weatherConnect, WeatherMapper weatherMapper) {
        this.weatherRepository = weatherRepository;
        this.weatherConnect = weatherConnect;
        this.weatherMapper = weatherMapper;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(fixedRateString = "${request.frequency.seconds}", timeUnit = TimeUnit.SECONDS)
    @Override
    public void saveWeatherInfo() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", key);
        headers.put("X-RapidAPI-Host", host);

        WeatherRequest weatherRequest = weatherConnect.findWeather(city, headers);
        Weather currentWeatherInfo = weatherMapper.weatherRequestToWeather(weatherRequest);
        weatherRepository.save(currentWeatherInfo);
    }
}