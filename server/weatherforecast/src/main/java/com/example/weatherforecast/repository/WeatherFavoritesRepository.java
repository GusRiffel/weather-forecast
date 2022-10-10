package com.example.weatherforecast.repository;

import com.example.weatherforecast.domain.WeatherFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeatherFavoritesRepository extends JpaRepository<WeatherFavorites, UUID> {
}
