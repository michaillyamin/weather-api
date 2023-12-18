package com.example.weatherapi.service;

import com.example.weatherapi.connector.WeatherConnect;
import com.example.weatherapi.mapper.WeatherMapper;
import com.example.weatherapi.model.dto.WeatherRequest;
import com.example.weatherapi.model.entity.Location;
import com.example.weatherapi.model.entity.Weather;
import com.example.weatherapi.model.entity.WeatherCondition;
import com.example.weatherapi.model.entity.WeatherData;
import com.example.weatherapi.repository.WeatherRepository;
import com.example.weatherapi.service.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherConnect weatherConnect;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Value("${base.city}")
    private String city;

    @Value("${base.key}")
    private String key;

    @Value("${base.host}")
    private String host;

    @Test
    void updateWeather_shouldSaveCurrentWeatherInfo() {
        Location location = new Location(1L, "Minsk");
        WeatherData weatherData = new WeatherData(1L, 1D, new Date(1702752301000L), 9.4, 1024D, 100, new WeatherCondition(1L, "Clear"), location);

        WeatherRequest weatherRequest = new WeatherRequest(weatherData, location);
        Weather weather = new Weather(1L, weatherData);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", key);
        headers.put("X-RapidAPI-Host", host);

        when(weatherConnect.findWeather(city, headers)).thenReturn(weatherRequest);
        when(weatherMapper.weatherRequestToWeather(weatherRequest)).thenReturn(weather);

        scheduleService.saveWeatherInfo();

        verify(weatherMapper, times(1)).weatherRequestToWeather(weatherRequest);
        verify(weatherRepository, times(1)).save(weather);
        verify(weatherConnect, times(1)).findWeather(city, headers);
    }

}
