package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.exception.BadRequestException;
import com.example.weatherforecast.repository.WeatherFavoritesRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WeatherFavoritesService {

    final
    WeatherFavoritesRepository weatherFavoritesRepository;

    public WeatherFavoritesService(WeatherFavoritesRepository weatherFavoritesRepository) {
        this.weatherFavoritesRepository = weatherFavoritesRepository;
    }

    public List<String> listAllCitiesByUsername(String username) {
        return weatherFavoritesRepository.findAllCitiesByUsername(username).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public void deleteCityByUsername(WeatherFavorites weatherFavorites) {
        weatherFavoritesRepository.delete(weatherFavoritesRepository.findByUsernameAndCity(weatherFavorites.getUsername(),
                weatherFavorites.getCity()).orElseThrow(() -> new BadRequestException("Weather not found")));
    }

    @Transactional
    public WeatherFavorites save(WeatherFavorites weatherFavorites) {
        return weatherFavoritesRepository.save(weatherFavorites);
    }
}
