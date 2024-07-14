package com.example.foodordering.repositories;

import com.example.foodordering.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "userWithUserInfo")
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}