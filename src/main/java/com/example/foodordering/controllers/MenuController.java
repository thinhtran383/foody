package com.example.foodordering.controllers;

import com.example.foodordering.entities.Menu;
import com.example.foodordering.services.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/getById/{id}")
    public ResponseEntity<Optional<Menu>> getMenuById(@PathVariable int id){
        return ResponseEntity.ok().body(menuService.getMenuById(id));
    }
}
