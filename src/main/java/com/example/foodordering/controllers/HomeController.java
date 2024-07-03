package com.example.foodordering.controllers;

import com.example.foodordering.request.NotificationMessage;
import com.example.foodordering.services.CloudinaryService;
import com.example.foodordering.services.fcm.FirebaseMessagingService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping()
    public String home() {
        return """
                Welcome to FoodOrdering!:)
                Please check the API documentation at /api-docs
                Please check the API documentation at /swagger-ui.html
                Please check the API documentation at /redoc.html
                """;
    }


    @PostMapping()
    @OpenAPI30
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) {
        Map<String, String> data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/notification")
    public String sendNotificationByToken(){

        NotificationMessage notificationMessage = NotificationMessage.builder()
                .recipientToken("dcQnqTMuQ1e0TAm-yiKY8y:APA91bGKfyf2IJNrTIprc54ZMFn5VX_kGxJQ7Am65THkqyOo4M-SgziIpCvzUIYAGE1R7OqRTfjuJTflAsZbAJ0MhEYt_-G2ZukYTWxv3PtLaXG7IBOYMXt5Lz_quB9-M_5OMop4BEiY")
                .title("thinh")
                .body("body")
                .image("image")
                .build();
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }


}
