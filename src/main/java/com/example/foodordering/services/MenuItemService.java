package com.example.foodordering.services;

import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.repositories.MenuItemRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public Page<MenuItem> getAllMenuItems(Pageable pageable){
        return menuItemRepository.findAll(pageable);
    }
}
