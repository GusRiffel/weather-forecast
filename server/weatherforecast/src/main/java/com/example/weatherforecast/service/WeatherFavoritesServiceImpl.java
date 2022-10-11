package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.repository.WeatherFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherFavoritesServiceImpl implements WeatherFavoritesService{

    @Autowired
    WeatherFavoritesRepository weatherFavoritesRepository;

    @Override
    public List<String> listAllCitiesByUsername(String username) {
        return weatherFavoritesRepository.findAllCitiesByUsername(username);
    }

    public WeatherFavorites save(WeatherFavorites weatherFavorites) {
        return weatherFavoritesRepository.save(weatherFavorites);
    }
}
