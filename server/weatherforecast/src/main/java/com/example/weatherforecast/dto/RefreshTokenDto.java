package com.example.weatherforecast.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class RefreshTokenDto {
    private UUID id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "RefreshToken cannot be empty")
    private String refreshToken;
}
