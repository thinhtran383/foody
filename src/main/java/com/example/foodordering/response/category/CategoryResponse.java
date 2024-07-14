package com.example.foodordering.response.category;

import com.example.foodordering.dtos.CategoryDTO;
import com.example.foodordering.entities.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CategoryResponse {
    private List<Category> categories;
    private int totalPages;

}
