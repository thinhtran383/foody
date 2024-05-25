package com.example.foodordering.services;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.repositories.MenuItemRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Page<MenuDTO> getAllMenuItems(@NotNull Pageable pageable) {
        Page<MenuItem> menuItems = menuItemRepository.findAll(pageable);
        return menuItems.map(menuItem -> modelMapper.map(menuItem, MenuDTO.class));
    }




}
