package com.example.weatherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

@Entity
@Table(name = "weather_condition")
public class WeatherCondition {

    @Id
    @Column(name = "weather_condition_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionId;

    @JsonProperty("text")
    @Column(name = "condition_text")
    @NotEmpty(message = "condition of weather shouldn't be empty")
    private String conditionText;

    public WeatherCondition() {}

    public WeatherCondition(Long conditionId, String conditionText) {
        this.conditionId = conditionId;
        this.conditionText = conditionText;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherCondition that = (WeatherCondition) o;

        if (!Objects.equals(conditionId, that.conditionId)) return false;
        return Objects.equals(conditionText, that.conditionText);
    }

    @Override
    public int hashCode() {
        int result = conditionId != null ? conditionId.hashCode() : 0;
        result = 31 * result + (conditionText != null ? conditionText.hashCode() : 0);
        return result;
    }
}
