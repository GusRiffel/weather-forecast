package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }
}
