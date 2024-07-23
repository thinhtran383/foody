package com.example.foodordering.response.user;

import com.example.foodordering.entities.User;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@Slf4j
public class UserResponse {
    @Hidden
    private int id;

    private String username;

    private String fullname;

    private String email;

    private String phoneNumber;

    private String address;

    public static UserResponse fromUser(User user){
       if(user == null){
           log.error("UserResponse {}", (Object) null);
           return null;
       }


        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }


}
