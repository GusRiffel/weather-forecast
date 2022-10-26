package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteCreateDto;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;
import com.example.weatherforecast.service.WeatherFavoritesService;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WeatherFavoritesControllerTest {

    @InjectMocks
    private WeatherFavoritesController weatherFavoritesController;

    @Mock
    ModelMapper modelMapperMock;

    @Mock
    WeatherFavoritesService weatherFavoritesServiceMock;

    @BeforeEach
    void setUp() {
        List<String> weatherFavoritesCitiesList = List.of("London", "Berlin");

        BDDMockito.when(modelMapperMock.map(ArgumentMatchers.any(WeatherFavoriteCreateDto.class),
                        ArgumentMatchers.any()))
                .thenReturn(WeatherFavoriteCreator.weatherFavoriteToBeSaved());

        BDDMockito.when(modelMapperMock.map(ArgumentMatchers.any(WeatherFavoriteDeleteDto.class), ArgumentMatchers.any()))
                .thenReturn(WeatherFavoriteCreator.createValidWeatherFavorite());

        BDDMockito.when(modelMapperMock.map(ArgumentMatchers.any(WeatherFavorites.class), ArgumentMatchers.any()))
                .thenReturn(WeatherFavoriteCreator.createWeatherFavoriteCreateDto());

        BDDMockito.when(weatherFavoritesServiceMock.listAllCitiesByUsername(ArgumentMatchers.anyString()))
                .thenReturn(weatherFavoritesCitiesList);

        BDDMockito.when(weatherFavoritesServiceMock.save(ArgumentMatchers.any(WeatherFavorites.class)))
                .thenReturn(WeatherFavoriteCreator.createValidWeatherFavorite());

        BDDMockito.doNothing().when(weatherFavoritesServiceMock).deleteCityByUsername(ArgumentMatchers.any(WeatherFavorites.class));
    }

    @Test
    @DisplayName("findaAll returns a list of string cities by username when successful")
    void findAll_ReturnsListOfStringCitiesByUsername_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.weatherFavoritesController.findAll(
                WeatherFavoriteCreator.createValidWeatherFavorite().getUsername()))
                .doesNotThrowAnyException();

        String expectedCity = WeatherFavoriteCreator.createValidWeatherFavorite().getCity();
        List<String> favoriteCitiesByUsername = this.weatherFavoritesController.findAll("Gus").getBody();

        Assertions.assertThat(favoriteCitiesByUsername).isNotNull().isNotEmpty().hasSize(2);
        Assertions.assertThat(favoriteCitiesByUsername.get(0)).isEqualTo(expectedCity);
    }

    @Test
    @DisplayName("save returns weatherFavoriteCreateDto when successful")
    void save_ReturnsWeatherFavoriteCreateDto_WhenSuccessful() {
        WeatherFavoriteCreateDto createDto =
                this.weatherFavoritesController.save(WeatherFavoriteCreator.createWeatherFavoriteCreateDto()).getBody();

        Assertions.assertThat(createDto).isNotNull().isEqualTo(WeatherFavoriteCreator.createWeatherFavoriteCreateDto());
    }

    @Test
    @DisplayName("delete removes weatherFavorite when successful")
    void delete_RemovesWeatherFavorite_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.weatherFavoritesController.delete(WeatherFavoriteCreator.createWeatherFavoriteDeleteDto()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity =
                this.weatherFavoritesController.delete(WeatherFavoriteCreator.createWeatherFavoriteDeleteDto());

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}