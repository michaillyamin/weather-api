package com.example.weatherapi.connector;

import com.example.weatherapi.model.dto.WeatherRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(url = "${base.url}", name = "weather-client")
@Component
public interface WeatherConnect {

    @RequestMapping(method = RequestMethod.GET)
    WeatherRequest findWeather(@RequestParam("q") String city,
                               @RequestHeader Map<String, String> headers);
}