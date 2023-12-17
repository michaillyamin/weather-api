package com.example.weatherapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

public class AverageWeatherRequest {

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotEmpty(message = "from-date shouldn't be empty")
    @Length(max = 10, message = "from-date length must be smaller than 10 symbols")
    private String from;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotEmpty(message = "to-date shouldn't be empty")
    @Length(max = 10, message = "to-date length must be smaller than 10 symbols")
    private String to;

    public AverageWeatherRequest () {}

    public AverageWeatherRequest(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AverageWeatherRequest that = (AverageWeatherRequest) o;

        if (!Objects.equals(from, that.from)) return false;
        return Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
