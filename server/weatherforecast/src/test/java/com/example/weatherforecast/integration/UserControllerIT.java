package com.example.weatherforecast.integration;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;
import com.example.weatherforecast.repository.UserRepository;
import com.example.weatherforecast.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("listAll returns list of usersDto when successful")
    void listAll_ReturnsListOfUsersDto_WhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        String expectedUsername = savedUser.getUsername();

        List<UserDto> userDtoList = testRestTemplate.exchange("/user/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserDto>>() {
        }).getBody();

        Assertions.assertThat(userDtoList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(userDtoList.get(0).getUsername()).isEqualTo(expectedUsername);
    }

    @Test
    @DisplayName("findById returns userDto when successful")
    void findById_ReturnsUserDto_WhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        UUID expectedId = savedUser.getId();

        UserDto userDto = testRestTemplate.getForObject("/user/{id}", UserDto.class, expectedId);

        Assertions.assertThat(userDto).isNotNull();
        Assertions.assertThat(userDto.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save return userDto when successful")
    void save_ReturnsUserDto_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();
        ResponseEntity<UserDto> userDtoResponseEntity = testRestTemplate.postForEntity("/user/createuser",
                userToBeSaved, UserDto.class);

        Assertions.assertThat(userDtoResponseEntity).isNotNull();
        Assertions.assertThat(userDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(Objects.requireNonNull(userDtoResponseEntity.getBody()).getId()).isNotNull();
    }

    @Test
    @DisplayName("update updates userDto when successful")
    void update_UpdatesUserDto_WhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());
        savedUser.setUsername("Carls");

        ResponseEntity<UserDto> userDtoResponseEntity = testRestTemplate.exchange("/user/update/{id}", HttpMethod.PUT,
                new HttpEntity<>(savedUser), UserDto.class, savedUser.getId());

        Assertions.assertThat(userDtoResponseEntity).isNotNull();
        Assertions.assertThat(Objects.requireNonNull(userDtoResponseEntity.getBody()).getUsername()).isEqualTo(savedUser.getUsername());
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful() {
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/user/delete/{id}", HttpMethod.DELETE, null,
                Void.class, savedUser.getId());

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
