package com.example.foodordering.controllers;

import com.example.foodordering.dtos.MenuDTO;
import com.example.foodordering.dtos.MenuUpdateDTO;
import com.example.foodordering.entities.MenuItem;
import com.example.foodordering.response.Response;
import com.example.foodordering.response.menu.ListMenuItemResponse;
import com.example.foodordering.response.menu.MenuItemResponse;
import com.example.foodordering.services.CloudinaryService;
import com.example.foodordering.services.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${api.v1.prefix}/menu")
@Tag(name = "MenuController", description = "Operations pertaining to menu items in Food Ordering System")
public class MenuController {
    private final MenuItemService menuItemService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/all")
    @Operation(summary = "Get menu items", responses = {
            @ApiResponse(responseCode = "200", description = "Menu items retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))})

    public ResponseEntity<Response> getMenuItems(
            @RequestParam(defaultValue = "0", required = false)
            int page,
            @RequestParam(defaultValue = "5", required = false)
            int limit
    ) {

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

    ) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<MenuItemResponse> menuItem = menuItemService.getMenuByCategory(pageable, category);

        ListMenuItemResponse response = ListMenuItemResponse.builder()
                .totalPages(menuItem.getTotalPages())
                .menuItemResponseList(menuItem.getContent())
                .build();

        return ResponseEntity.ok().body(new Response("success", "Roles retrieved successfully", response));
    }

    @PostMapping(value = "/getUrlImage", consumes = {"multipart/form-data"})
    public ResponseEntity<Response> getUrlImage(@RequestPart MultipartFile image) {
        String url = cloudinaryService.upload(image);
        return ResponseEntity.ok().body(new Response("success", "Image uploaded successfully", url));
    }

    @PostMapping("/createItem")
    public ResponseEntity<Response> createItem(@RequestBody @Valid MenuDTO menuDTO) {
        log.error(menuDTO.toString());

        try {
            return ResponseEntity.ok().body(new Response("success", "Menu item created successfully", menuItemService.createNewMenuItem(menuDTO)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<Response> updateItem(@PathVariable Integer id, @RequestBody @Valid MenuUpdateDTO menuDTO) {
        log.error(menuDTO.toString());

        try {
            return ResponseEntity.ok().body(new Response("success", "Menu item updated successfully", menuItemService.updateMenuItem(id, menuDTO)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<Response> deleteItem(@PathVariable Integer id){

        try {
            return ResponseEntity.ok().body(new Response("success", "deleted", menuItemService.deleteMenuItem(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

}
