package com.example.weatherapi.service.impl;

import com.example.weatherapi.model.exception.CannotCalculateAverageWeatherDataException;
import com.example.weatherapi.model.exception.CustomDateParsingException;
import com.example.weatherapi.mapper.WeatherMapper;
import com.example.weatherapi.model.dto.AverageWeatherRequest;
import com.example.weatherapi.model.dto.AverageWeatherResponse;
import com.example.weatherapi.model.dto.WeatherResponse;
import com.example.weatherapi.model.entity.Weather;
import com.example.weatherapi.model.entity.WeatherData;
import com.example.weatherapi.model.exception.WeatherDataNotFoundException;
import com.example.weatherapi.repository.WeatherRepository;
import com.example.weatherapi.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Service
@Validated
@Transactional(readOnly = true)
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherMapper weatherMapper) {
        this.weatherRepository = weatherRepository;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public WeatherResponse findLatestData() {
        Optional<Weather> weatherOptional = weatherRepository.findTopByOrderByWeatherIdDesc();

        return weatherOptional.map(weather -> {
            WeatherData latestData = weather.getWeatherData();
            return weatherMapper.weatherDataToWeatherResponse(latestData);
        }).orElseThrow(() -> new WeatherDataNotFoundException("Weather data not found"));
    }

    @Override
    public AverageWeatherResponse findAverageWeatherData(@Valid AverageWeatherRequest averageWeatherRequest) {
        Date from = parseDate(averageWeatherRequest.getFrom());
        Date to = parseDate(averageWeatherRequest.getTo());

        List<Weather> weatherForPeriod = weatherRepository.findByWeatherDataLastUpdateBetween(from, to);
        return calculateAverageWeather(weatherForPeriod);
    }

    private Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new CustomDateParsingException("Failed to parse the date: " + date);
        }
    }

    private AverageWeatherResponse calculateAverageWeather(List<Weather> weatherForPeriod) {

        if (weatherForPeriod.isEmpty()) {
            throw new WeatherDataNotFoundException("Weather data not found");
        }

        double averageTemperature = getAverageValue(weather -> weather.getWeatherData().getAirTemperature(), weatherForPeriod);
        double averageWindSpeed = getAverageValue(weather -> weather.getWeatherData().getWindSpeed(), weatherForPeriod);
        double averageAtmospherePressure = getAverageValue(weather -> weather.getWeatherData().getAtmospherePressure(), weatherForPeriod);
        double averageHumidity = getAverageValue(weather -> weather.getWeatherData().getHumidity(), weatherForPeriod);

        return new AverageWeatherResponse(
                String.format("%.2f", averageTemperature),
                String.format("%.2f", averageWindSpeed),
                String.format("%.2f", averageAtmospherePressure),
                String.format("%.2f", averageHumidity)
        );
    }

    private <T> double getAverageValue(Function<Weather, T> averageToFind, List<Weather> weatherForPeriod) {

        return weatherForPeriod.stream()
                .map(averageToFind)
                .mapToDouble(value -> ((Number) value).doubleValue())
                .average()
                .orElseThrow(() -> new CannotCalculateAverageWeatherDataException("Exception during calculation average value"));
    }
}
