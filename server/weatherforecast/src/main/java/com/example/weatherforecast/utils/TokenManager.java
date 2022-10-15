package com.example.weatherforecast.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class TokenManager {

    private static String jwtSecret;

    @Value("${secret}")
    public void setTest(String secret) {
        jwtSecret = secret;
    }

    public static Algorithm createAlgorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    private static String createAccessToken(HttpServletRequest request, User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(createAlgorithm());
    }

    private static String createRefreshToken(HttpServletRequest request, User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(createAlgorithm());
    }

    public static Map<String, String> createNewToken(HttpServletRequest request, User user) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("username", user.getUsername());
        tokens.put("access_token", TokenManager.createAccessToken(request, user));
        tokens.put("refresh_token", TokenManager.createRefreshToken(request, user));
        return tokens;
    }


    public static void verifyToken(String authHeader) {
        JWTVerifier verifier = JWT.require(createAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(authHeader.substring("Bearer ".length()));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


}

