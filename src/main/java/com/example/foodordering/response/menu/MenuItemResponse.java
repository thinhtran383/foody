package com.example.foodordering.response.menu;

import com.example.foodordering.entities.MenuItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemResponse {
    private int id;
    private String itemName;
    private BigDecimal price;
    private String image;
    private String category;
}
