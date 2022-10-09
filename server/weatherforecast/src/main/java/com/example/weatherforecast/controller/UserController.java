package com.example.weatherforecast.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;
import com.example.weatherforecast.service.UserService;
import com.example.weatherforecast.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> listAll() {
        List<UserDto> userDtoList = userService.listAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        User user = userService.getById(id);
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    @CrossOrigin(origins = "http://127.0.0.1:5173/")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
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
