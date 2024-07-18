package com.example.foodordering.repositories;


import com.example.foodordering.entities.Token;
import com.example.foodordering.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{
    Token findByToken(String token);
    Token findByUser(User user);

    long deleteByUser(User user);
}
