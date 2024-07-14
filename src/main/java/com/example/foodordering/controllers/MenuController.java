package com.example.foodordering.controllers;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.response.Response;
import com.example.foodordering.response.menu.ListMenuItemResponse;
import com.example.foodordering.response.menu.MenuItemResponse;
import com.example.foodordering.services.MenuItemService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.v1.prefix}/menu")
@Tag(name = "MenuController", description = "Operations pertaining to menu items in Food Ordering System")
public class MenuController {
    private final MenuItemService menuItemService;


    @GetMapping()
    @ApiResponse(content = @Content(schema = @Schema(implementation = MenuDTO.class)))
    public ResponseEntity<Response> getMenuItems(
            @RequestParam(defaultValue = "0", required = false)
            int page,
            @RequestParam(defaultValue = "5", required = false)
            int limit
    ) {
        log.error("{} {}", page, limit);

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());

        Page<MenuItemResponse> menuItems = menuItemService.getAllMenuItems(pageable);
        int totalPages = menuItems.getTotalPages();

        ListMenuItemResponse response = ListMenuItemResponse.builder()
                .menuItemResponseList(menuItems.getContent())
                .totalPages(totalPages)
                .build();

        return ResponseEntity.ok().body(
                new Response("success", "MenuItem retrieved successfully", response)
        );
    }

    @GetMapping("/{category}")
    public ResponseEntity<Response> getMenuItemByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0", required = false)
            int page,
            @RequestParam(defaultValue = "5", required = false)
            int limit

    ){
        Pageable pageable =  PageRequest.of(page, limit);
        Page<MenuItemResponse> menuItem = menuItemService.getMenuByCategory(pageable, category);

        ListMenuItemResponse response = ListMenuItemResponse.builder()
                .totalPages(menuItem.getTotalPages())
                .menuItemResponseList(menuItem.getContent())
                .build();

        return ResponseEntity.ok().body(new Response("success", "Roles retrieved successfully", response));
    }

}
