package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.User;

import java.util.List;

public interface UserService {
    List<User> listAll();

    User save(User user);

    User update(long id, User user);

    void delete(long id);

    User getById(long id);
}
