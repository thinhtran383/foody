package com.example.foodordering.response.user;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Getter
@Setter
@ToString
public class LoginResponse {
    private String name;
    private String email;
    private String phone;
    private String address;
    private List<String> role;
    private String token;
}
