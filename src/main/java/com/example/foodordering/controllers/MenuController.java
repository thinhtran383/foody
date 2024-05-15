package com.example.foodordering.controllers;

import com.example.foodordering.entities.Menu;
import com.example.foodordering.services.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Menu", description = "Menu API")
@RequestMapping("${api.v1.prefix}/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("")
    public ResponseEntity<List<Menu>> getAllMenu() {
        List<Menu> menus = menuService.getAllMenus();
        return ResponseEntity.ok().body(menus);
    }
}
