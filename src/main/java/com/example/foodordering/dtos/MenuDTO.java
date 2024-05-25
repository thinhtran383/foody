package com.example.foodordering.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuDTO {
    private int id;
    private String name;
    private BigDecimal price;
    private String image;
    private String categoryName;
}
