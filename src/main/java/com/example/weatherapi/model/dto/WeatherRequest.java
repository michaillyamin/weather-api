package com.example.weatherapi.model.dto;

import com.example.weatherapi.model.entity.Location;
import com.example.weatherapi.model.entity.WeatherData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WeatherRequest {

    @JsonProperty("current")
    private WeatherData weatherData;

    @JsonProperty("location")
    private Location location;

    public WeatherRequest() {}

    public WeatherRequest(WeatherData weatherData, Location location) {
        this.weatherData = weatherData;
        this.location = location;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherRequest that = (WeatherRequest) o;

        if (!Objects.equals(weatherData, that.weatherData)) return false;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        int result = weatherData != null ? weatherData.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
