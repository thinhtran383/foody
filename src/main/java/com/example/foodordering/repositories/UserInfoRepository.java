package com.example.foodordering.repositories;

import com.example.foodordering.entities.User;
import com.example.foodordering.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    boolean existsByPhone(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByEmailOrPhone(String email, String phone);
}
