package com.example.foodordering.response.orders;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@ToString
public class OrderDetailResponse {
    private int id;
    private String item;
    private Integer quantity;
    private BigDecimal price;

}
