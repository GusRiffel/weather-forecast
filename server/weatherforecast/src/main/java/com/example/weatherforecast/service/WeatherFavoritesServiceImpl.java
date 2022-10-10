package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.repository.WeatherFavoritesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class WeatherFavoritesServiceImpl implements WeatherFavoritesService{

    @Autowired
    WeatherFavoritesRepository weatherFavoritesRepository;

    @Override
    public List<String> listAll(String username) {
        List<WeatherFavorites> weatherFavoritesList = weatherFavoritesRepository.findAll();
        return weatherFavoritesList.stream()
                .filter(w -> w.getUsername().equals(username))
                .map(WeatherFavorites::getCity)
                .toList();
    }

    public WeatherFavorites save(WeatherFavorites weatherFavorites) {
        return weatherFavoritesRepository.save(weatherFavorites);
    }
}
