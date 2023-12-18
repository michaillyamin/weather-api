package com.example.weatherapi.mapper;

import com.example.weatherapi.model.dto.WeatherRequest;
import com.example.weatherapi.model.dto.WeatherResponse;
import com.example.weatherapi.model.entity.Location;
import com.example.weatherapi.model.entity.Weather;
import com.example.weatherapi.model.entity.WeatherCondition;
import com.example.weatherapi.model.entity.WeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mapping(target = "weatherData", source = "weatherData")
    @Mapping(target = "weatherData.location", source = "location")
    Weather weatherRequestToWeather(WeatherRequest weatherRequest);

    @Mapping(target = "airTemperature", source = "airTemperature")
    @Mapping(target = "windSpeed", source = "windSpeed")
    @Mapping(target = "atmospherePressure", source = "atmospherePressure")
    @Mapping(target = "humidity", source = "humidity")
    @Mapping(target = "weatherCondition", qualifiedByName = "mapWeatherCondition", source = "weatherCondition")
    @Mapping(target = "location", qualifiedByName = "mapLocation", source = "location")
    WeatherResponse weatherDataToWeatherResponse(WeatherData weatherData);

    @Named("mapLocation")
    default String getLocationNameConverter(Location location) {
        return location.getCityName();
    }

    @Named("mapWeatherCondition")
    default String getWeatherConditionTextConverter(WeatherCondition weatherCondition) {
        return weatherCondition.getConditionText();
    }
}
