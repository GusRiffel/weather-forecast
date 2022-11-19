package com.example.weatherforecast.service;

import com.example.weatherforecast.domain.RefreshToken;
import com.example.weatherforecast.exception.BadRequestException;
import com.example.weatherforecast.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken save(@RequestBody RefreshToken refreshToken) {
        if (isTokenAlreadyExistent(refreshToken.getUsername())) {
            throw new BadRequestException("Token already exists for the given user");
        } else {
            return refreshTokenRepository.save(refreshToken);
        }
    }

    public void delete(UUID id) {
        refreshTokenRepository.deleteById(id);
    }

    public RefreshToken getByUsername(String username) {
        return refreshTokenRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("User not " +
                        "found"));
    }

    public boolean isTokenAlreadyExistent(String username) {
        try {
            getByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
