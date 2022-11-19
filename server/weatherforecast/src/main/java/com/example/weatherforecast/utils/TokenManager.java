package com.example.weatherforecast.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@Log4j2
public class TokenManager {

    private static String accessTokenSecret;
    private static String refreshTokenSecret;

    @Value("${access_secret}")
    public void setAccessTokenSecret(String secret) {
        accessTokenSecret = secret;
    }

    @Value("${refresh_secret}")
    public void setRefreshTokenSecret(String secret) {
        refreshTokenSecret = secret;
    }

    private static Algorithm createAlgorithm(String secret) {
        return Algorithm.HMAC256(secret.getBytes());
    }

    private static String createAccessToken(HttpServletRequest request, String user) {
        return JWT.create()
                .withSubject(user)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(createAlgorithm(accessTokenSecret));
    }

    private static String createRefreshToken(HttpServletRequest request, String user) {
        return JWT.create()
                .withSubject(user)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1440 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(createAlgorithm(refreshTokenSecret));
    }

    public static Map<String, String> createNewToken(HttpServletRequest request, User user) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("username", user.getUsername());
        tokens.put("access_token", TokenManager.createAccessToken(request, user.getUsername()));
        tokens.put("refresh_token", TokenManager.createRefreshToken(request, user.getUsername()));
        return tokens;
    }

    public static String refreshAccessToken(String user, HttpServletRequest request) {
        return createAccessToken(request, user);
    }

    public static DecodedJWT verifyAccessToken(String authHeader) {
        JWTVerifier verifier = JWT.require(createAlgorithm(accessTokenSecret)).build();
        return verifier.verify(authHeader.substring("Bearer ".length()));
    }

    public static DecodedJWT verifyRefreshToken(String authHeader) {
        JWTVerifier verifier = JWT.require(createAlgorithm(refreshTokenSecret)).build();
        return verifier.verify(authHeader.substring("Bearer ".length()));
    }

    public static void setUserToSpringSecurity(DecodedJWT decodedJWT) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}

