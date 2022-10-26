package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.repository.WeatherFavoritesRepository;
import com.example.weatherforecast.util.UserCreator;
import com.example.weatherforecast.util.WeatherFavoriteCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WeatherFavoritesServiceTest {

    @InjectMocks
    WeatherFavoritesService weatherFavoritesService;

    @Mock
    WeatherFavoritesRepository weatherFavoritesRepositoryMock;

    @BeforeEach
    void setUp() {
        List<String> weatherFavoritesCitiesList = List.of("London", "Berlin");

        BDDMockito.when(weatherFavoritesRepositoryMock.findAllCitiesByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(weatherFavoritesCitiesList));

        BDDMockito.when(weatherFavoritesRepositoryMock.findByUsernameAndCity(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(WeatherFavoriteCreator.createValidWeatherFavorite()));

        BDDMockito.doNothing().when(weatherFavoritesRepositoryMock).delete(ArgumentMatchers.any(WeatherFavorites.class));

        BDDMockito.when(weatherFavoritesRepositoryMock.save(ArgumentMatchers.any(WeatherFavorites.class)))
                .thenReturn(WeatherFavoriteCreator.createValidWeatherFavorite());
    }

    @Test
    @DisplayName("listAllCitiesByUsername returns list of string cities when successful")
    void listAllCitiesByUsername_ReturnsListOfStringCities_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.weatherFavoritesService.listAllCitiesByUsername(
                        WeatherFavoriteCreator.createValidWeatherFavorite().getUsername()))
                .doesNotThrowAnyException();

        String expectedCity = WeatherFavoriteCreator.createValidWeatherFavorite().getCity();
        List<String> favoriteCitiesByUsername = this.weatherFavoritesService.listAllCitiesByUsername("Gus");

        Assertions.assertThat(favoriteCitiesByUsername).isNotNull().isNotEmpty().hasSize(2);
        Assertions.assertThat(favoriteCitiesByUsername.get(0)).isEqualTo(expectedCity);
    }

    @Test
    void deleteCityByUsername() {
        Assertions.assertThatCode(() -> this.weatherFavoritesService.deleteCityByUsername(WeatherFavoriteCreator.createValidWeatherFavorite()))
                .doesNotThrowAnyException();
    }

    @Test
    void save() {
        WeatherFavorites savedFavorite =
                this.weatherFavoritesRepositoryMock.save(WeatherFavoriteCreator.weatherFavoriteToBeSaved());

        Assertions.assertThat(savedFavorite).isNotNull();
        Assertions.assertThat(savedFavorite.getId()).isEqualTo(WeatherFavoriteCreator.createValidWeatherFavorite().getId());
        Assertions.assertThat(savedFavorite.getUsername()).isEqualTo(WeatherFavoriteCreator.createValidWeatherFavorite().getUsername());
        Assertions.assertThat(savedFavorite.getCity()).isEqualTo(WeatherFavoriteCreator.createValidWeatherFavorite().getCity());
    }
}