package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.WeatherFavorites;
import com.example.weatherforecast.dto.WeatherFavoriteDeleteDto;
import com.example.weatherforecast.service.WeatherFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/favorites")
public class WeatherFavoritesController {

    @Autowired
    private WeatherFavoritesService weatherFavoritesService;

    @GetMapping("/{username}")
    public ResponseEntity<List<String>> findAll(@PathVariable String username) {
        return new ResponseEntity<>(weatherFavoritesService.listAllCitiesByUsername(username), HttpStatus.OK );
    }

    @PostMapping("/create")
    public ResponseEntity<WeatherFavorites> save(@RequestBody @Valid WeatherFavorites weatherFavorites) {
        return new ResponseEntity<>(weatherFavoritesService.save(weatherFavorites), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody WeatherFavoriteDeleteDto deleteDto) {
        weatherFavoritesService.deleteCityByUsername(deleteDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
