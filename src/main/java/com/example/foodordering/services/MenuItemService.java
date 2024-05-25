package com.example.foodordering.services;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.dtos.CategoryDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public Page<MenuDTO> getAllMenuItems(Pageable pageable) {
        Page<MenuItem> menuItems = menuItemRepository.findAll(pageable);
        return menuItems.map(this::convertToDTO);
    }

    private MenuDTO convertToDTO(MenuItem menuItem) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getItemName());
        dto.setImage(menuItem.getImage());
        dto.setPrice(menuItem.getPrice());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(menuItem.getCategory().getCategoryName());
        dto.setCategoryName(categoryDTO.getName());

        return dto;
    }
}
