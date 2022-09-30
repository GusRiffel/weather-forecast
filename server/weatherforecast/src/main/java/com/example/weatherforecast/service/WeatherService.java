package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {
    @Value("${apiKey}")
    private String apiKey;
    private final WebClient webClient;

    public WeatherService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<Weather> getWeatherForecastByCity(String cityName) {
        return webClient.get()
                .uri("weather?q=" + cityName + "&appid=" + apiKey + "&units=metric")
                .retrieve()
                .bodyToMono(Weather.class);
    }
}
