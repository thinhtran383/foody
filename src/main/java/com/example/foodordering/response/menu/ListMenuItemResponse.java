package com.example.foodordering.response.menu;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class ListMenuItemResponse {
    private int totalPages;
    private List<MenuItemResponse> menuItemResponseList;
}
