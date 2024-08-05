package com.example.foodordering.repositories;

import com.example.foodordering.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "userWithRoles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findByUsername(String username);

    @EntityGraph(value = "userWithRoles", type = EntityGraph.EntityGraphType.FETCH)
    Page<User> findAll(Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmailOrPhoneNumber(String email, String phone);
}
