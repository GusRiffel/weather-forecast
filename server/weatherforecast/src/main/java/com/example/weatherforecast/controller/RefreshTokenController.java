package com.example.weatherforecast.controller;

import com.example.weatherforecast.domain.RefreshToken;
import com.example.weatherforecast.dto.RefreshTokenDto;
import com.example.weatherforecast.service.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("token")
public class RefreshTokenController {

    private final ModelMapper modelMapper;
    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(ModelMapper modelMapper, RefreshTokenService refreshTokenService) {
        this.modelMapper = modelMapper;
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<RefreshTokenDto> findByUsername(@PathVariable String username) {
        RefreshToken refreshToken = refreshTokenService.getByUsername(username);
        RefreshTokenDto tokenResponse = modelMapper.map(refreshToken, RefreshTokenDto.class);

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RefreshTokenDto> saveRefreshToken(@RequestBody @Valid RefreshTokenDto dto) {
        RefreshToken tokenRequest = modelMapper.map(dto, RefreshToken.class);
        RefreshToken refreshToken = refreshTokenService.save(tokenRequest);

        RefreshTokenDto tokenResponse = modelMapper.map(refreshToken, RefreshTokenDto.class);
        return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{username}")
    public ResponseEntity<Void> deleteRefreshToken(@PathVariable String username) {
        RefreshToken refreshToken = refreshTokenService.getByUsername(username);
        refreshTokenService.delete(refreshToken.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
