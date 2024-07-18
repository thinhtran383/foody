package com.example.foodordering.response.user;

import com.example.foodordering.entities.User;
import com.example.foodordering.entities.UserInfo;
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


        UserInfo userInfo = user.getUserInfo();

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullname(userInfo.getName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhone())
                .address(userInfo.getAddress())
                .build();
    }


}
