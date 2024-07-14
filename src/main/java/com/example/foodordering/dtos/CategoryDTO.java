package com.example.foodordering.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CategoryDTO {
    @Hidden
    private Long id;

    @NotEmpty(message = "Category's name cannot be empty")
    private String name;

}
