package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> listAll();

    User save(User user);

    User update(UUID id, User user);

    void delete(UUID id);

    User getById(UUID id);
}
