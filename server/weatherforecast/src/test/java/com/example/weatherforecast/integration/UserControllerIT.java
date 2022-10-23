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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
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
        userRepository.save(UserCreator)

        Assertions.assertThat(userDto).isNotNull();
        Assertions.assertThat(userDto.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save return userDto when successful")
    void save_ReturnsUserDto_WhenSuccessful() {
        UserDto userDto = this.userController.save(UserCreator.createValidUserDto()).getBody();

        Assertions.assertThat(userDto).isNotNull().isEqualTo(UserCreator.createValidUserDto());
    }

    @Test
    @DisplayName("update updates userDto when successful")
    void update_UpdatesUserDto_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.userController.update(uuid, UserCreator.createValidUserDto()))
                .doesNotThrowAnyException();

        UserDto userUpdatedDto = this.userController.update(uuid, UserCreator.createValidUserDto()).getBody();

        Assertions.assertThat(userUpdatedDto).isNotNull().isEqualTo(UserCreator.createValidUserDto());
    }

    @Test
    @DisplayName("delete removes user when succesful")
    void delete_RemovesUser_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.userController.delete(uuid))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = this.userController.delete(uuid);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
