package com.example.weatherforecast.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Getter
public class Weather {
    private String city;

    private WeatherData weatherData;

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
        if (windData.get("speed").equals(0)) {
            this.windSpeed = 0.0;
        } else {
            this.windSpeed = (Double) windData.get("speed");
        }
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
