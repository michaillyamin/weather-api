package com.example.weatherapi.controller;

import com.example.weatherapi.model.dto.AverageWeatherRequest;
import com.example.weatherapi.model.dto.AverageWeatherResponse;
import com.example.weatherapi.model.dto.WeatherResponse;
import com.example.weatherapi.service.impl.WeatherServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherServiceImpl weatherService;

    @InjectMocks
    private WeatherController weatherController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getLatestWeatherData_shouldReturnWeatherResponse() throws Exception {
        WeatherResponse expectedResponse = new WeatherResponse("1", "9.4", "1024", "100", "Clear", "Minsk");

        when(weatherService.findLatestData()).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/weather"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.air_temperature").value("1"))
                .andExpect(jsonPath("$.wind_speed").value("9.4"))
                .andExpect(jsonPath("$.atmosphere_pressure").value("1024"))
                .andExpect(jsonPath("$.humidity").value("100"))
                .andExpect(jsonPath("$.weather_condition").value("Clear"))
                .andExpect(jsonPath("$.location").value("Minsk"));
        verify(weatherService, times(1)).findLatestData();
    }

    @Test
    void getAverageWeatherData_shouldReturnAverageWeatherResponse() throws Exception {
        AverageWeatherRequest weatherRequest = new AverageWeatherRequest("16-12-2023", "17-12-2023");
        String weatherRequestJson = objectMapper.writeValueAsString(weatherRequest);

        AverageWeatherResponse expectedResponse = new AverageWeatherResponse("0,23", "9,08", "1023,49", "95,51");

        when(weatherService.findAverageWeatherData(weatherRequest)).thenReturn(expectedResponse);

        mockMvc.perform(get("/api/weather/average")
                .contentType(MediaType.APPLICATION_JSON)
                .content(weatherRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average_temperature").value("0,23"))
                .andExpect(jsonPath("$.average_wind_speed").value("9,08"))
                .andExpect(jsonPath("$.average_atmosphere_pressure").value("1023,49"))
                .andExpect(jsonPath("$.average_humidity").value("95,51"));
        verify(weatherService, times(1)).findAverageWeatherData(weatherRequest);
    }

}