package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;
import com.example.weatherforecast.repository.WeatherFavoritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherFavoritesServiceImpl {

    @Autowired
    WeatherFavoritesRepository weatherFavoritesRepository;

    public List<String> listAllCitiesByUsername(String username) {
        return weatherFavoritesRepository.findAllCitiesByUsername(username);
    }

    public void deleteCityByUsername(WeatherFavoriteDeleteDto deleteDto) {
        weatherFavoritesRepository.delete(weatherFavoritesRepository.findByUsernameAndCity(deleteDto.getUsername(),
                deleteDto.getCity()));
    }

    public WeatherFavorites save(WeatherFavorites weatherFavorites) {
        return weatherFavoritesRepository.save(weatherFavorites);
    }
}
