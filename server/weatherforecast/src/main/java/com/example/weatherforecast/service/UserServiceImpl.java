package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;
import com.example.weatherforecast.exception.BadRequestException;
import com.example.weatherforecast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(long id, User userRequest) {
        User user = getById(id);
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(getById(id));
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }
}
