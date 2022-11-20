package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.User;
import com.example.weatherforecast.exception.BadRequestException;
import com.example.weatherforecast.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(@RequestBody User user) {
        if (isUsernameAlreadyExistent(user.getUsername())) {
            throw new BadRequestException("Username already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    public User update(UUID id, User userRequest) {
        User user = getById(id);
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.delete(getById(id));
    }

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + username));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

    public boolean isUsernameAlreadyExistent(String username) {
        try {
            getByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
