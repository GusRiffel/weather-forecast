package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> listAll() {
        return userService.listAll();
    }

    @PostMapping("/createuser")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public User save(@RequestBody @Valid User user) {
        return userService.save(user);
    }
}
