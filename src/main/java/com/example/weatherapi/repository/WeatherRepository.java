package com.example.weatherapi.repository;

import com.example.weatherapi.model.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findTopByOrderByWeatherIdDesc();

    List<Weather> findByWeatherDataLastUpdateBetween(Date fromDate, Date toDate);
}
