package com.example.foodordering.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.foodordering.entities.Token;
import com.example.foodordering.entities.User;
import com.example.foodordering.repositories.TokenRepository;
import com.example.foodordering.repositories.UserRepository;
import com.example.foodordering.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtGenerator {
    @Value("${jwt.secret.key}")
    private String secretKey;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public String generateToken(User user) {
//        User foundUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + 864000000))
                .sign(Algorithm.HMAC256(secretKey));

        return token;
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJwt = getDecodedJwt(token);
        return decodedJwt.getSubject();
    }


    public List<String> extractRoles(String token) {
        DecodedJWT decodedJwt = getDecodedJwt(token);

        return decodedJwt.getClaim("roles").asList(String.class);
    }

    public boolean isValidToken(String token, User userDetails) {
        String username = extractUsername(token);
        Token existingToken = tokenRepository.findByToken(token);

        if(token == null || existingToken == null){
            return false;
        }
        return username.equals(userDetails.getUsername());
    }


    private DecodedJWT getDecodedJwt(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
    }


}
