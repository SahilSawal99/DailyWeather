package com.example.dailyweather.controller;

import com.example.dailyweather.dto.WeatherResponse;
import com.example.dailyweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponse> getCurrentWeatherByCity(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "units", required = false) String units
    ) {
        WeatherResponse response = weatherService.getCurrentWeatherByCity(city, units);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
