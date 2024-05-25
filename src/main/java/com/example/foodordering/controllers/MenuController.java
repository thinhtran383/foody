package com.example.foodordering.controllers;

import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.services.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1.prefix}/menu")
public class MenuController {
    private final MenuItemService menuItemService;
    @GetMapping()
    public ResponseEntity<?> getMenuItems(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue ="5", required = false) int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuItem> menuItems = menuItemService.getAllMenuItems(pageable);

        return ResponseEntity.ok(menuItems.getContent());
    }

}
