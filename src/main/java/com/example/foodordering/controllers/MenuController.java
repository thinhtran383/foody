package com.example.foodordering.controllers;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.services.MenuItemService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "MenuController", description = "Operations pertaining to menu items in Food Ordering System")
public class MenuController {
    private final MenuItemService menuItemService;


    @GetMapping()
    @ApiResponse(content = @Content(schema = @Schema(implementation = MenuDTO.class)))
    public ResponseEntity<?> getMenuItems(
            @RequestParam(defaultValue = "0", required = false)
            int page,
            @RequestParam(defaultValue = "5", required = false)
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MenuDTO> menuItems = menuItemService.getAllMenuItems(pageable);

        return ResponseEntity.ok(menuItems.getContent());
    }

}
