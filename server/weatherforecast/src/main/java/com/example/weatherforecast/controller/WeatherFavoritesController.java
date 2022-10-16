package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;
import com.example.weatherforecast.service.WeatherFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class WeatherFavoritesController {

    private final WeatherFavoritesService weatherFavoritesService;

    public WeatherFavoritesController(WeatherFavoritesService weatherFavoritesService) {
        this.weatherFavoritesService = weatherFavoritesService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public ResponseEntity<List<String>> findAll(@PathVariable String username) {
        return new ResponseEntity<>(weatherFavoritesService.listAllCitiesByUsername(username), HttpStatus.OK );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<WeatherFavorites> save(@RequestBody @Valid WeatherFavorites weatherFavorites) {
        return new ResponseEntity<>(weatherFavoritesService.save(weatherFavorites), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody WeatherFavoriteDeleteDto deleteDto) {
        weatherFavoritesService.deleteCityByUsername(deleteDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
