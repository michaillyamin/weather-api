package com.example.weatherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @Column(name = "weather_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherDataId;

    @JsonProperty("temp_c")
    @Column(name = "air_temperature")
    private Double airTemperature;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonProperty("last_updated")
    @Column(name = "last_update")
    private Date lastUpdate;

    @JsonProperty("wind_mph")
    @Column(name = "wind_speed")
    private Double windSpeed;

    @JsonProperty("pressure_mb")
    @Column(name = "atmosphere_pressure")
    private Double atmospherePressure;

    @JsonProperty("humidity")
    @Column(name = "humidity")
    private Integer humidity;

    @JsonProperty("condition")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_condition_id")
    private WeatherCondition weatherCondition;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    public WeatherData() {}

    public WeatherData(Long weatherDataId, Double airTemperature, Date lastUpdate, Double windSpeed, Double atmospherePressure, Integer humidity, WeatherCondition weatherCondition, Location location) {
        this.weatherDataId = weatherDataId;
        this.airTemperature = airTemperature;
        this.lastUpdate = lastUpdate;
        this.windSpeed = windSpeed;
        this.atmospherePressure = atmospherePressure;
        this.humidity = humidity;
        this.weatherCondition = weatherCondition;
        this.location = location;
    }

    public Long getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(Long weatherDataId) {
        this.weatherDataId = weatherDataId;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getAtmospherePressure() {
        return atmospherePressure;
    }

    public void setAtmospherePressure(Double atmospherePressure) {
        this.atmospherePressure = atmospherePressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
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

        WeatherData that = (WeatherData) o;

        if (!Objects.equals(weatherDataId, that.weatherDataId))
            return false;
        if (!Objects.equals(airTemperature, that.airTemperature))
            return false;
        if (!Objects.equals(lastUpdate, that.lastUpdate)) return false;
        if (!Objects.equals(windSpeed, that.windSpeed)) return false;
        if (!Objects.equals(atmospherePressure, that.atmospherePressure))
            return false;
        if (!Objects.equals(humidity, that.humidity)) return false;
        if (!Objects.equals(weatherCondition, that.weatherCondition))
            return false;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        int result = weatherDataId != null ? weatherDataId.hashCode() : 0;
        result = 31 * result + (airTemperature != null ? airTemperature.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (windSpeed != null ? windSpeed.hashCode() : 0);
        result = 31 * result + (atmospherePressure != null ? atmospherePressure.hashCode() : 0);
        result = 31 * result + (humidity != null ? humidity.hashCode() : 0);
        result = 31 * result + (weatherCondition != null ? weatherCondition.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
