package com.example.weatherapi.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @Column(name = "weather_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_data_id")
    private WeatherData weatherData;

    public Weather() {}

    public Weather(Long weatherId, WeatherData weatherData) {
        this.weatherId = weatherId;
        this.weatherData = weatherData;
    }

    public Long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weather weather = (Weather) o;

        if (!Objects.equals(weatherId, weather.weatherId)) return false;
        return Objects.equals(weatherData, weather.weatherData);
    }

    @Override
    public int hashCode() {
        int result = weatherId != null ? weatherId.hashCode() : 0;
        result = 31 * result + (weatherData != null ? weatherData.hashCode() : 0);
        return result;
    }
}
