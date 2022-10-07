package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;
import com.example.weatherforecast.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> listAll() {
        List<UserDto> userDtoList = userService.listAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        User user = userService.getById(id);
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/createuser")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto) {
        User userRequest = modelMapper.map(userDto, User.class);
        User user = userService.save(userRequest);

        UserDto userResponse = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody UserDto userDto) {
        User userRequest = modelMapper.map(userDto, User.class);
        User user = userService.update(id, userRequest);

        UserDto userResponse = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
