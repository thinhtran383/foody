package com.example.foodordering.services;

import com.example.foodordering.dtos.CategoryDTO;
import com.example.foodordering.entities.Category;
import com.example.foodordering.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<Category> getAllCategories(Pageable pageable){
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories;
    }

    @Transactional
    public Category createCategory(CategoryDTO categoryDTO){
        Optional<Category> category = categoryRepository.findByCategoryName(categoryDTO.getName());

        return category.orElseGet(() -> categoryRepository.save(modelMapper.map(categoryDTO, Category.class)));
    }
}
