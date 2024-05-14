package com.example.foodordering.controllers;

import com.example.foodordering.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final CloudinaryService cloudinaryService;

    @GetMapping()
    public String home() {
        return "Welcome to FoodOrdering! :)\n Thinh, Hai, Ngoc, Toan";
    }


    @PostMapping
    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file) {
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
