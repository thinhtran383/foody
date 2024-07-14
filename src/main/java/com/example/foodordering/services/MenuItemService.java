package com.example.foodordering.services;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.entities.Category;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.repositories.CategoryRepository;
import com.example.foodordering.repositories.MenuItemRepository;
import com.example.foodordering.response.menu.MenuItemResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.awt.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getAllMenuItems(@NotNull Pageable pageable) {
        Page<MenuItem> menuItems = menuItemRepository.findAll(pageable);
        return menuItems.map(menuItem -> modelMapper.map(menuItem, MenuItemResponse.class));
    }

    @Transactional(readOnly = true)
    public MenuItem getMenuItemById(@NotNull Integer menuItemId) {
        return menuItemRepository.findById(menuItemId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<MenuItemResponse> getMenuByCategory(@NotNull Pageable pageable, String category){
        Page<MenuItem> menuItems = menuItemRepository.findMenuItemByCategory_CategoryName(pageable, category);

        return menuItems.map((element) -> modelMapper.map(element, MenuItemResponse.class));
    }

    @Transactional
    public MenuItemResponse addNewMenuItem(@NotNull MenuDTO menuDTO) throws Exception {
        MenuItem menuItem = modelMapper.map(menuDTO, MenuItem.class);

        Category category = categoryRepository.findByCategoryName(menuDTO.getCategoryName());


        if(category == null) {
           throw new DataNotFoundException("Category not found");
        }

        menuItem.setCategory(category);

        return modelMapper.map(menuItemRepository.save(menuItem), MenuItemResponse.class);
    }

    public MenuItemResponse updateMenuItem(Integer id, MenuDTO menuDTO)  throws Exception{
       MenuItem menuItem = menuItemRepository.findById(id).orElseThrow();
            Category category = categoryRepository.findByCategoryName(menuDTO.getCategoryName());

            if(category == null) {
                throw new DataNotFoundException("Category not found");
            }

            if(menuDTO.getItemName() != null) {
                menuItem.setItemName(menuDTO.getItemName());
            }





            return modelMapper.map(menuItemRepository.save(menuItem), MenuItemResponse.class);
    }
}
