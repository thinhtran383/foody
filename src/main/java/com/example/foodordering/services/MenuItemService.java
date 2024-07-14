package com.example.foodordering.services;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.repositories.MenuItemRepository;
import com.example.foodordering.response.menu.MenuItemResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
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
    public void addNewMenuItem(@NotNull MenuItem menuItem) {
        menuItemRepository.save(menuItem);
    }
}
