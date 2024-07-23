package com.example.foodordering.services;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.dtos.MenuUpdateDTO;
import com.example.foodordering.entities.Category;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.repositories.CategoryRepository;
import com.example.foodordering.repositories.MenuItemRepository;
import com.example.foodordering.repositories.OrderDetailRepository;
import com.example.foodordering.response.menu.MenuItemResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.awt.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemService {
    private final OrderDetailRepository orderDetailRepository;

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
    public Page<MenuItemResponse> getMenuByCategory(@NotNull Pageable pageable, String category) {
        Page<MenuItem> menuItems = menuItemRepository.findMenuItemByCategory_CategoryName(pageable, category);

        return menuItems.map((element) -> modelMapper.map(element, MenuItemResponse.class));
    }

    @Transactional
    public MenuItemResponse createNewMenuItem(@NotNull MenuDTO menuDTO) throws Exception {

        // check category existed
        Category category = categoryRepository.findByCategoryName(menuDTO.getCategoryName()).orElseThrow(() -> new DataNotFoundException("Category not found"));

        Optional<MenuItem> existingItem = menuItemRepository.findByItemNameAndCategory_CategoryName(menuDTO.getItemName(), menuDTO.getCategoryName());


        if (existingItem.isPresent()) {
            throw new DataIntegrityViolationException("Item already existed");
        }

        MenuItem menuItem = modelMapper.map(menuDTO, MenuItem.class);
        menuItem.setCategory(category);

        return modelMapper.map(menuItemRepository.save(menuItem), MenuItemResponse.class);

    }

    @Transactional
    public MenuItemResponse updateMenuItem(Integer id, MenuUpdateDTO menuDTO) throws Exception {
        MenuItem existingItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Menu item not found"));

        Category category = categoryRepository.findByCategoryName(menuDTO.getCategoryName())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));


        if (menuDTO.getItemName() != null) {
            existingItem.setItemName(menuDTO.getItemName());
        }

        if (menuDTO.getPrice() != null) {
            existingItem.setPrice(menuDTO.getPrice());
        }

        if (menuDTO.getImage() != null) {
            existingItem.setImage(menuDTO.getImage());
        }

        existingItem.setCategory(category);

        return modelMapper.map(menuItemRepository.save(existingItem), MenuItemResponse.class);

    }

    @Transactional
    public MenuItemResponse deleteMenuItem(Integer id) throws Exception {
        MenuItem menuItem = menuItemRepository.findMenuItemById(id).orElseThrow(() -> new DataNotFoundException("Item not existed"));

        menuItem.getOrderDetails().forEach(orderDetail -> {
            orderDetail.setItem(null);
            orderDetailRepository.save(orderDetail);

        });


        menuItemRepository.deleteById(id);
        return modelMapper.map(menuItem, MenuItemResponse.class);
    }
}
