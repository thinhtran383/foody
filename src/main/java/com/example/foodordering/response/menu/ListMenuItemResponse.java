package com.example.foodordering.response.menu;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListMenuItemResponse {
    private int totalPages;
    private List<MenuItemResponse> menuItemResponseList;
}
