package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.Weather;
import com.example.weatherforecast.exception.BadRequestException;
import com.example.weatherforecast.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.function.ServerResponse;
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
                .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(),
                        response -> Mono.error(new ServiceException("City doesn't exist",
                                response.statusCode().value())))
                .bodyToMono(Weather.class);
    }
}
