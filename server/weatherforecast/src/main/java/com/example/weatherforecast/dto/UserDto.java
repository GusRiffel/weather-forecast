package com.example.weatherforecast.dto;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String username;
    private String email;
    private String password;
}
