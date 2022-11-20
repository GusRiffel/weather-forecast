package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteCreateDto;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;
import com.example.weatherforecast.service.WeatherFavoritesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class WeatherFavoritesController {

    private final WeatherFavoritesService weatherFavoritesService;
    private final ModelMapper modelMapper;

    public WeatherFavoritesController(WeatherFavoritesService weatherFavoritesService, ModelMapper modelMapper) {
        this.weatherFavoritesService = weatherFavoritesService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public ResponseEntity<List<String>> findAll(@PathVariable String username) {
        return new ResponseEntity<>(weatherFavoritesService.listAllCitiesByUsername(username), HttpStatus.OK );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<WeatherFavoriteCreateDto> save(@RequestBody @Valid WeatherFavoriteCreateDto createDto) {
        WeatherFavorites weatherRequest = modelMapper.map(createDto, WeatherFavorites.class);
        WeatherFavorites weatherFavorite = weatherFavoritesService.save(weatherRequest);

        WeatherFavoriteCreateDto weatherResponse = modelMapper.map(weatherFavorite, WeatherFavoriteCreateDto.class);
        return new ResponseEntity<>(weatherResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody WeatherFavoriteDeleteDto deleteDto) {
        WeatherFavorites weatherRequest = modelMapper.map(deleteDto, WeatherFavorites.class);
        weatherFavoritesService.deleteCityByUsername(weatherRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
