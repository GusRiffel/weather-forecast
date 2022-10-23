package com.example.weatherforecast.util;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;

import java.util.UUID;

public class UserCreator {

    static UUID uuid = UUID.randomUUID();

    public static User createUserToBeSaved() {
        return User.builder()
                .username("Gus")
                .email("gus@gus.com")
                .password("123456")
                .build();
    }

    public static User createValidUser() {
        return User.builder()
                .id(uuid)
                .username("Gus")
                .email("gus@gus.com")
                .password("123456")
                .build();
    }

    public static UserDto createValidUserDto() {
        return UserDto.builder()
                .id(createValidUser().getId())
                .username(createValidUser().getUsername())
                .email(createValidUser().getEmail())
                .password(createValidUser().getPassword())
                .build();
    }


    public static User createValidUpdatedUser() {
        return User.builder()
                .id(uuid)
                .username("Carls")
                .email("carls@calrs.com")
                .password("654321")
                .build();
    }

    public static UserDto createValidUpdatedUserDto() {
        return UserDto.builder()
                .id(createValidUpdatedUser().getId())
                .username(createValidUpdatedUser().getUsername())
                .email(createValidUpdatedUser().getEmail())
                .password(createValidUpdatedUser().getPassword())
                .build();
    }
}
