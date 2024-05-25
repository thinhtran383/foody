package com.example.foodordering.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDetailDTO {

    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Order ID is required")
    private Integer menuItemId;

    @NotNull(message = "Order ID is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantityUpdate;
}
