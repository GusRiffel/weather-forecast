package com.example.weatherforecast.repository;

import com.example.weatherforecast.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@DisplayName("UserRepository tests")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save persists user when successful")
    void save_PersistUser_WhenSuccessful() {
        User userToBeSaved = createUser();
        User savedUser = this.userRepository.save(userToBeSaved);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(userToBeSaved.getUsername());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(userToBeSaved.getEmail());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(userToBeSaved.getPassword());
    }

    @Test
    @DisplayName("Save updates user when successful")
    void save_UpdatesUser_WhenSuccessful() {
        User userToBeSaved = createUser();
        User savedUser = this.userRepository.save(userToBeSaved);

        savedUser.setUsername("Carls");
        savedUser.setEmail("carls@carls.com");
        savedUser.setPassword("654321");
        User updatedUser = this.userRepository.save(savedUser);

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(updatedUser.getUsername());
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(updatedUser.getEmail());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo(updatedUser.getPassword());
    }

    @Test
    @DisplayName("Delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful() {
        User userToBeSaved = createUser();
        User savedUser = this.userRepository.save(userToBeSaved);

        this.userRepository.delete(savedUser);
        Optional<User> userOptional = this.userRepository.findByUsername(savedUser.getUsername());

        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by Username returns user when successful")
    void findByUsername_ReturnsUser_WhenSuccessful() {
        User userToBeSaved = createUser();
        User savedUser = this.userRepository.save(userToBeSaved);

        String username = savedUser.getUsername();
        Optional<User> user = this.userRepository.findByUsername(username);

        Assertions.assertThat(user).isNotEmpty();
    }

    @Test
    @DisplayName("Find by Username returns empty when not found")
    void findByUsername_ReturnsEmpty_WhenNotFound() {
        Optional<User> user = this.userRepository.findByUsername("zxc");

        Assertions.assertThat(user).isEmpty();
    }


    private User createUser() {
        return User.builder()
                .username("Gus")
                .email("gus@gus.com")
                .password("123456")
                .build();
    }
}