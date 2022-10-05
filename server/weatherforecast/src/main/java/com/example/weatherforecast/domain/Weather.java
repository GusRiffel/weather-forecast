package com.example.weatherforecast.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
public class Weather {
    private String city;

    private WeatherData weatherData;

    @JsonProperty("weatherCondition")
    private String weatherMain;

    @JsonProperty("windSpeed")
    private Double windSpeed;

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        this.weatherMain = (String) weather.get("main");
    }

    @JsonProperty("wind")
    public void setWind(Map<String, Object> windData) {
        this.windSpeed = (Double) windData.get("speed");
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.city = name;
    }

    @JsonProperty("main")
    public void setMain(WeatherData weatherData) {
        this.weatherData = weatherData;
    }
}
