package com.example.weatherforecast.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "weatherFavorites")
public class WeatherFavorites {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotEmpty
    private String city;

    @Column(nullable = false)
    @NotEmpty
    private String username;
}
