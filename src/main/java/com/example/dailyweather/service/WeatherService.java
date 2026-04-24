package com.example.dailyweather.service;

import com.example.dailyweather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${openweather.base-url}")
    private String baseUrl;

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.default-city:Delhi}")
    private String defaultCity;

    @Value("${openweather.units:metric}")
    private String defaultUnits;

    public WeatherResponse getCurrentWeatherByCity(String city, String units) {
        String resolvedCity = (city == null || city.isBlank()) ? defaultCity : city.trim();
        String resolvedUnits = (units == null || units.isBlank()) ? defaultUnits : units.trim();

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing OpenWeather API key. Set env var OPENWEATHER_API_KEY.");
        }

        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/weather")
                .queryParam("q", resolvedCity)
                .queryParam("units", resolvedUnits)
                .queryParam("appid", apiKey)
                .build()
                .encode()
                .toUriString();

        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
