package com.example.weatherforecast.repository;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.exception.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("WeatherFavorites repository test")
class WeatherFavoritesRepositoryTest {
    @Autowired
    WeatherFavoritesRepository weatherFavoritesRepository;

    @Test
    @DisplayName("Save persist Weatherfavorite when successful")
    void save_PersistsWeatherFavorite_WhenSuccessful() {
        WeatherFavorites weatherToBeSaved = createFavoriteWeather();
        WeatherFavorites savedWeather = weatherFavoritesRepository.save(weatherToBeSaved);

        Assertions.assertThat(savedWeather).isNotNull();
        Assertions.assertThat(savedWeather.getId()).isNotNull();
        Assertions.assertThat(savedWeather.getUsername()).isEqualTo(weatherToBeSaved.getUsername());
        Assertions.assertThat(savedWeather.getCity()).isEqualTo(weatherToBeSaved.getCity());
    }

    @Test
    @DisplayName("Save updates Weatherfavorite when successful")
    void save_UpdatesWeatherFavorite_WhenSuccessful() {
        WeatherFavorites weatherToBeSaved = createFavoriteWeather();
        WeatherFavorites savedWeather = weatherFavoritesRepository.save(weatherToBeSaved);

        savedWeather.setUsername("Carls");
        savedWeather.setCity("Berlin");
        WeatherFavorites updatedWeather = this.weatherFavoritesRepository.save(savedWeather);

        Assertions.assertThat(updatedWeather).isNotNull();
        Assertions.assertThat(updatedWeather.getId()).isNotNull();
        Assertions.assertThat(savedWeather.getUsername()).isEqualTo(updatedWeather.getUsername());
        Assertions.assertThat(savedWeather.getCity()).isEqualTo(updatedWeather.getCity());
    }

    @Test
    @DisplayName("Delete removes Weatherfavorite when successful")
    void delete_RemovesWeatherFavorite_WhenSuccessful() {
        WeatherFavorites weatherToBeSaved = createFavoriteWeather();
        WeatherFavorites savedWeather = weatherFavoritesRepository.save(weatherToBeSaved);

        this.weatherFavoritesRepository.delete(savedWeather);
        Optional<WeatherFavorites> optionalWeather = this.weatherFavoritesRepository.findById(savedWeather.getId());

        Assertions.assertThat(optionalWeather).isEmpty();
    }

    @Test
    @DisplayName("Find by username and city returns Weatherfavorite when successful")
    void findByUserNameAndCity_ReturnsWeatherFavorite_WhenSuccessful() {
        WeatherFavorites weatherToBeSaved = createFavoriteWeather();
        WeatherFavorites savedWeather = weatherFavoritesRepository.save(weatherToBeSaved);


        Optional<WeatherFavorites> optionalWeatherFavorite =
                this.weatherFavoritesRepository.findByUsernameAndCity(savedWeather.getUsername(),
                        savedWeather.getCity());

        Assertions.assertThat(optionalWeatherFavorite).isNotEmpty();
    }

    @Test
    @DisplayName("Find by username and city returns empty when not found")
    void findByUserNameAndCity_ReturnsEmpty_WhenNotFound() {
        Optional<WeatherFavorites> optionalWeatherFavorite =
                this.weatherFavoritesRepository.findByUsernameAndCity("aa","bb");

        Assertions.assertThat(optionalWeatherFavorite).isEmpty();
    }

    private WeatherFavorites createFavoriteWeather() {
        return WeatherFavorites.builder()
                .username("Gus")
                .city("London")
                .build();
    }
}