package com.example.foodordering.repositories;

import com.example.foodordering.entities.UserRole;
import com.example.foodordering.entities.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}