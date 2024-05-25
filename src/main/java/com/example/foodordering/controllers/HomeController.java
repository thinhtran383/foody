package com.example.foodordering.controllers;

import com.example.foodordering.services.CloudinaryService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Tag(name = "Root", description = "Root for testing the API")
public class HomeController {
    private final CloudinaryService cloudinaryService;

    @GetMapping()
    public String home() {
        return "Welcome to FoodOrdering! :)\n Thinh, Hai, Ngoc, Toan";
    }


    @PostMapping()
    @OpenAPI30
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) {
        Map<String, String> data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
