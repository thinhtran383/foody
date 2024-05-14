package com.example.foodordering.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @RequestMapping
    public String home() {
        return "Welcome to FoodOrdering! :)\n Thinh, Hai, Ngoc, Toan";
    }
}
