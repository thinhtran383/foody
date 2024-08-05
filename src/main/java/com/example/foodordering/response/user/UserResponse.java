package com.example.foodordering.response.user;

import com.example.foodordering.entities.Role;
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
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @Hidden
    private int id;

    private String username;

    private String fullname;

    private String email;

    private String phoneNumber;

    private String address;
    private String roles;

    public static UserResponse fromUser(User user){
       if(user == null){
           log.error("UserResponse {}", (Object) null);
           return null;
       }


        return UserResponse.builder()
//                .roles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                .id(user.getId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }


}
