package com.example.weatherforecast.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
public class Weather {
    private String city;

    private Instant dateTime;

    private WeatherData weatherData;

    @JsonProperty("weatherCondition")
    private String weatherMain;

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        this.weatherMain = (String) weather.get("main");
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.city = name;
    }

    @JsonProperty("dt")
    public void setDt(String dt) {
        this.dateTime = Instant.ofEpochSecond(Long.parseLong(dt));
    }

    @JsonProperty("main")
    public void setMain(WeatherData weatherData) {
        this.weatherData = weatherData;
    }
}
