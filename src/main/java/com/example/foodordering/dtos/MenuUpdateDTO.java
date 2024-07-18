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
public class MenuUpdateDTO {
    private int id;
    @JsonAlias("name")
    private String itemName;

    private BigDecimal price;

    private String image;

    private String categoryName;
}
