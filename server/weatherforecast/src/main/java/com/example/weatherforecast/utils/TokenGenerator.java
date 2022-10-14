package com.example.weatherforecast.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


public class TokenGenerator {

    public static String createAccessToken(HttpServletRequest request, User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 10 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public static String createRefreshToken(HttpServletRequest request, User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

}

