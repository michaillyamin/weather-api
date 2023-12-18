package com.example.weatherapi.service;

import com.example.weatherapi.mapper.WeatherMapper;
import com.example.weatherapi.model.dto.AverageWeatherRequest;
import com.example.weatherapi.model.dto.AverageWeatherResponse;
import com.example.weatherapi.model.dto.WeatherResponse;
import com.example.weatherapi.model.entity.Location;
import com.example.weatherapi.model.entity.Weather;
import com.example.weatherapi.model.entity.WeatherCondition;
import com.example.weatherapi.model.entity.WeatherData;
import com.example.weatherapi.model.exception.WeatherDataNotFoundException;
import com.example.weatherapi.repository.WeatherRepository;
import com.example.weatherapi.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void findLatestData_withValidWeatherData_savesWeatherData() {
        WeatherData weatherData = new WeatherData(1L, 1D, new Date(1702752301000L), 9.4, 1024D, 100, new WeatherCondition(1L, "Clear"), new Location(1L, "Minsk"));
        Weather weather = new Weather(1L, weatherData);
        WeatherResponse weatherResponse = new WeatherResponse("1", "9,4", "1024", "100", "Clear", "Minsk");

        when(weatherRepository.findTopByOrderByWeatherIdDesc()).thenReturn(Optional.of(weather));
        when(weatherMapper.weatherDataToWeatherResponse(weatherData)).thenReturn(weatherResponse);

        WeatherResponse resultResponse = weatherService.findLatestData();

        verify(weatherRepository, times(1)).findTopByOrderByWeatherIdDesc();
        verify(weatherMapper, times(1)).weatherDataToWeatherResponse(weatherData);

        assertNotNull(resultResponse);
        assertEquals(weatherResponse, resultResponse);
    }

    @Test
    void findLatestData_withWeatherDataNotFound_throwsException() {
        when(weatherRepository.findTopByOrderByWeatherIdDesc()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> weatherService.findLatestData())
                .isInstanceOf(WeatherDataNotFoundException.class)
                .hasMessage("Weather data not found");

        verify(weatherRepository, times(1)).findTopByOrderByWeatherIdDesc();
    }


    @Test
    void findAverageWeatherData_withValidRequest_returnsAverageWeatherData() throws ParseException {
        AverageWeatherRequest weatherRequest = new AverageWeatherRequest("16-12-2023", "17-12-2023");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date from = dateFormat.parse("16-12-2023");
        Date to = dateFormat.parse("17-12-2023");

        List<Weather> weatherData = List.of(
                new Weather(1L, new WeatherData(1L, -3D, from, 13.6D, 1023D, 80, new WeatherCondition(1L, "Clear"), new Location(1L, "Minsk"))),
                new Weather(2L, new WeatherData(2L, -1D, to, 12.2D, 1025D, 100, new WeatherCondition(2L, "Light rain"), new Location(2L, "Minsk")))
        );

        when(weatherRepository.findByWeatherDataLastUpdateBetween(from, to)).thenReturn(weatherData);

        AverageWeatherResponse weatherResponse = weatherService.findAverageWeatherData(weatherRequest);

        verify(weatherRepository, times(1)).findByWeatherDataLastUpdateBetween(from, to);

        assertNotNull(weatherResponse);

        double expectedAverageTemperature = -2;
        double expectedAverageWindSpeed = 12.9D;
        double expectedAverageAtmospherePressure = 1024D;
        double expectedAverageHumidity = 90;

        assertEquals(String.format("%.2f", expectedAverageTemperature), weatherResponse.averageTemperature());
        assertEquals(String.format("%.2f", expectedAverageWindSpeed), weatherResponse.averageWindSpeed());
        assertEquals(String.format("%.2f", expectedAverageAtmospherePressure), weatherResponse.averageAtmospherePressure());
        assertEquals(String.format("%.2f", expectedAverageHumidity), weatherResponse.averageHumidity());
    }

}
