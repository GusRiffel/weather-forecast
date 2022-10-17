package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;
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
        return weatherFavoritesRepository.findAllCitiesByUsername(username);
    }

    public void deleteCityByUsername(WeatherFavoriteDeleteDto deleteDto) {
        weatherFavoritesRepository.delete(weatherFavoritesRepository.findByUsernameAndCity(deleteDto.getUsername(),
                deleteDto.getCity()));
    }

    @Transactional
    public WeatherFavorites save(WeatherFavorites weatherFavorites) {
        return weatherFavoritesRepository.save(weatherFavorites);
    }
}
