package com.example.foodordering.response.user;

import com.example.foodordering.entities.User;
import com.example.foodordering.entities.UserInfo;
import lombok.*;

@Data
@Getter
@Setter
@Builder
public class UserResponse {
    private String username;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    public static UserResponse fromUser(User user){
        UserInfo userInfo = user.getUserInfo();
        return UserResponse.builder()
                .username(user.getUsername())
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhone())
                .address(userInfo.getAddress())
                .build();
    }
}
