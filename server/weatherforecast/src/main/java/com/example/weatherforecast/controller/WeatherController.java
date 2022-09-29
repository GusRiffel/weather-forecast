package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.service.WeatherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("weather")
@Log4j2
public class WeatherController {

    @Autowired
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{cityName}")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public Mono<Weather> weather(@PathVariable String cityName) {
        return weatherService.getWeatherForecastByCity(cityName);
    }
}
