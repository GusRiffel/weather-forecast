package com.example.weatherforecast.service;

import java.util.List;

public interface WeatherFavoritesService {

    List<String> listAllCitiesByUsername(String username);
}
