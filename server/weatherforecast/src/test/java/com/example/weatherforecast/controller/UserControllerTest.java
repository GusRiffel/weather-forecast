package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.dto.UserDto;
import com.example.weatherforecast.service.UserService;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    UUID uuid = UUID.randomUUID();

    @InjectMocks
    private UserController userController;

    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        List<User> usersList = List.of(UserCreator.createValidUser());

        BDDMockito.when(modelMapperMock.map(ArgumentMatchers.any(User.class), ArgumentMatchers.any()))
                .thenReturn(UserCreator.createValidUserDto());

        BDDMockito.when(modelMapperMock.map(ArgumentMatchers.any(UserDto.class), ArgumentMatchers.any()))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.listAll())
                .thenReturn(usersList);

        BDDMockito.when(userServiceMock.getById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("listAll returns list of usersDto when successful")
    void listAll_ReturnsListOfUsersDto_WhenSuccessful() {
        String expectedUsername = UserCreator.createValidUser().getUsername();
        List<UserDto> userDtoList = this.userController.listAll().getBody();

        Assertions.assertThat(userDtoList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(userDtoList.get(0).getUsername()).isEqualTo(expectedUsername);
    }

    @Test
    @DisplayName("findById returns userDto when successful")
    void findById_ReturnsUserDto_WhenSuccessful() {
        UUID expectedId = UserCreator.createValidUser().getId();
        UserDto userDto = this.userController.findById(uuid).getBody();

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