package com.example.foodordering.response.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class ListUserResponse {
    private int totalPages;
    private List<UserResponse> userResponses;
}
