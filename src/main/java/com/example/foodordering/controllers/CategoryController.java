package com.example.foodordering.controllers;

import com.example.foodordering.dtos.CategoryDTO;
import com.example.foodordering.entities.Category;
import com.example.foodordering.response.Response;
import com.example.foodordering.response.category.CategoryResponse;
import com.example.foodordering.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Response> getAll(
            @RequestParam(defaultValue = "0", required = false)
            int page,
            @RequestParam(defaultValue = "5", required = false)
            int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Category> categories = categoryService.getAllCategories(pageable);

        CategoryResponse response = new CategoryResponse();
        response.setCategories(categories.getContent());
        response.setTotalPages(categories.getTotalPages());

        return ResponseEntity.ok().body(
                new Response("success", "Category retrieved successfully", response)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok().body(
                new Response("success", "Category created successfully", category)
        );
    }
}
