package com.example.foodordering.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    @NotNull(message = "Menu ID is required")
    @Min(value = 1, message = "Menu ID must be greater than 0")
    private int itemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;


}
