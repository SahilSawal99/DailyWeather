package com.example.dailyweather.controller;

import com.example.dailyweather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WeatherController.class)
class WeatherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WeatherService weatherService;

    @Test
    void missingApiKeyReturnsReadableError() throws Exception {
        given(weatherService.getCurrentWeatherByCity(null, null))
                .willThrow(new IllegalStateException("Missing OpenWeather API key. Set env var OPENWEATHER_API_KEY."));

        mockMvc.perform(get("/api/v1/weather"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Missing OpenWeather API key. Set env var OPENWEATHER_API_KEY."))
                .andExpect(jsonPath("$.path").value("/api/v1/weather"));
    }
}

