package com.example.weatherforecast.repository;

import com.example.weatherforecast.domain.WeatherFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WeatherFavoritesRepository extends JpaRepository<WeatherFavorites, UUID> {

    @Query("SELECT w.city FROM WeatherFavorites w WHERE w.username = :username ")
    List<String> findAllCitiesByUsername(@Param("username") String username);

    WeatherFavorites findByUsernameAndCity(String username, String city);
}
