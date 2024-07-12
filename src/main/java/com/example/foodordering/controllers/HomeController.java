package com.example.foodordering.controllers;

import com.example.foodordering.dtos.UserDTO;
import com.example.foodordering.entities.Role;
import com.example.foodordering.entities.Table;
import com.example.foodordering.entities.User;
import com.example.foodordering.request.NotificationMessage;
import com.example.foodordering.services.CloudinaryService;
import com.example.foodordering.services.TokenService;
import com.example.foodordering.services.UserService;
import com.example.foodordering.services.fcm.FirebaseMessagingService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Tag(name = "Root", description = "Root for testing the API")
public class HomeController {
    private final CloudinaryService cloudinaryService;
    private final FirebaseMessagingService firebaseMessagingService;

    private final UserService userService;
    private final TokenService tokenService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/hello")
    public String home() {
        return """
                Welcome to FoodOrdering!:)
                Please check the API documentation at /api-docs
                Please check the API documentation at /swagger-ui.html
                Please check the API documentation at /redoc.html
                """;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestBody UserDTO userDTO) {
        try {
            String token = userService.login(userDTO.getUsername(), userDTO.getPassword());

            User user = userService.getUserDetailFromToken(token);

            tokenService.addToken(user, token);


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();

    }

    @PostMapping(consumes = {"multipart/form-data"})
    @OpenAPI30
    public ResponseEntity<?> uploadImage(
            @RequestPart("image") MultipartFile file,
            @RequestPart("infor") String name
    ) {
        Map<String, String> data = this.cloudinaryService.upload(file);
        System.out.println(name);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/notification")
    public ResponseEntity<?> sendNotificationByToken(@RequestBody NotificationMessage notificationMessage) {
        return ResponseEntity.ok().body(firebaseMessagingService.sendNotificationByToken(notificationMessage));
    }


}
