package com.example.foodordering.services;

import com.example.foodordering.entities.Token;
import com.example.foodordering.entities.User;
import com.example.foodordering.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public Token addToken(User user, String token){
        Token userToken = tokenRepository.findByUser(user);

        if(userToken != null){
            tokenRepository.delete(userToken);
        }

        long expiration = 86400;

        Token newToken = new Token();
        newToken.setUser(user);
        newToken.setToken(token);
        newToken.setExpirationDate(LocalDateTime.now().plusSeconds(expiration));
        return tokenRepository.save(newToken);
    }

}
