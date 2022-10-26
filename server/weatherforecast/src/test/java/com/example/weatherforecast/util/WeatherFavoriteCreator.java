package com.example.weatherforecast.util;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteCreateDto;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;

import java.util.UUID;

public class WeatherFavoriteCreator {

    static UUID uuid = UUID.randomUUID();

    public static WeatherFavorites weatherFavoriteToBeSaved() {
        return WeatherFavorites.builder()
                .city("London")
                .username("Gus")
                .build();
    }

    public static WeatherFavorites createValidWeatherFavorite() {
        return WeatherFavorites.builder()
                .id(uuid)
                .city("London")
                .username("Gus")
                .build();
    }

    public static WeatherFavoriteCreateDto createWeatherFavoriteCreateDto() {
        return WeatherFavoriteCreateDto.builder()
                .username("Gus")
                .city("London")
                .build();
    }


    public static WeatherFavoriteDeleteDto createWeatherFavoriteDeleteDto() {
        return WeatherFavoriteDeleteDto.builder()
                .username("Gus")
                .city("London")
                .build();
    }

}
