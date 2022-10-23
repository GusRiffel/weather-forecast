package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;
import com.example.weatherforecast.exception.BadRequestException;
import com.example.weatherforecast.repository.UserRepository;
import com.example.weatherforecast.util.UserCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
    UUID uuid = UUID.randomUUID();

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        List<User> usersList = List.of(UserCreator.createValidUser());

        BDDMockito.when(passwordEncoder.encode(ArgumentMatchers.anyString()))
                        .thenReturn("123456");

        BDDMockito.when(userRepositoryMock.findAll())
                .thenReturn(usersList);

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                        .thenReturn(Optional.of(UserCreator.createValidUser()));

        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(User.class));
    }

    @Test
    @DisplayName("listAll returns list of users when successful")
    void listAll_ReturnsListOfUsersDto_WhenSuccessful() {
        String expectedUsername = UserCreator.createValidUser().getUsername();
        List<User> userList = this.userService.listAll();

        Assertions.assertThat(userList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(userList.get(0).getUsername()).isEqualTo(expectedUsername);
    }

    @Test
    @DisplayName("save return user when successful")
    void save_ReturnsUser_WhenSuccessful() {
        User user = this.userService.save(UserCreator.createValidUser());

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(UserCreator.createValidUser().getId());
        Assertions.assertThat(user.getUsername()).isEqualTo(UserCreator.createValidUser().getUsername());
        Assertions.assertThat(user.getEmail()).isEqualTo(UserCreator.createValidUser().getEmail());
        Assertions.assertThat(user.getPassword()).isEqualTo(UserCreator.createValidUser().getPassword());
    }

    @Test
    @DisplayName("update updates User when successful")
    void update_UpdatesUser_WhenSuccessful() {
        Assertions.assertThatCode(() -> userService.update(uuid, UserCreator.createValidUpdatedUser()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful() {
        Assertions.assertThatCode(() -> this.userService.delete(uuid))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("getById returns user when successful")
    void getById_ReturnsUser_WhenSuccessful() {
        UUID expectedId = UserCreator.createValidUser().getId();
        User user = this.userService.getById(uuid);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("getById throws BadRequestException when user not found")
    void getById_ThrowsBadRequestException_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.getById(uuid));
    }

    @Test
    @DisplayName("getByUserName returns user when successful")
    void getByUserName_ReturnsUser_WhenSuccessful() {
        String expectedUsername = UserCreator.createValidUser().getUsername();
        User user = this.userService.getByUsername("Gus");

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo(expectedUsername);
    }

    @Test
    @DisplayName("getByUserName throws BadRequestException when user not found")
    void getByUserName_ThrowsBadRequestException_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findByUsername("Gus"))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.getByUsername("Gus"));
    }

}