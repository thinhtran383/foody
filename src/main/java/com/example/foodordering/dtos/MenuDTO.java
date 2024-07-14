package com.example.foodordering.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@Getter
@Setter
public class MenuDTO {
    @Hidden
    private int id;
    @NotBlank(message = "Name is required")
    @JsonAlias("name")
    private String itemName;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Image is required")
    private String image;

    @NotBlank(message = "Category name is required")
    private String categoryName;
}
