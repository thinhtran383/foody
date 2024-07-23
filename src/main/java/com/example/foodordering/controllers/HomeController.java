package com.example.foodordering.controllers;

import com.example.foodordering.dtos.NotificationMessageDTO;
import com.example.foodordering.response.Response;
import com.example.foodordering.services.CloudinaryService;
import com.example.foodordering.services.MenuItemService;
import com.example.foodordering.services.TokenService;
import com.example.foodordering.services.UserService;
import com.example.foodordering.services.fcm.FirebaseMessagingService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Tag(name = "Root", description = "Root for testing the API")
@Hidden
public class HomeController {
    private final CloudinaryService cloudinaryService;
    private final FirebaseMessagingService firebaseMessagingService;

    private final MenuItemService menuItemService;

    private final UserService userService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    @GetMapping("/test")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String home() {
        return """
                Welcome to FoodOrdering!:)
                Please check the API documentation at /api-docs
                Please check the API documentation at /swagger-ui.html
                Please check the API documentation at /redoc.html
                """;
    }

    @PostMapping("/test")
    public String homePost() {
        return """
                Welcome to FoodOrdering!:)
                Please check the API documentation at /api-docs
                Please check the API documentation at /swagger-ui.html
                Please check the API documentation at /redoc.html
                """;
    }
    @GetMapping("/pay-success")
    public ResponseEntity<Response> paySuccess() {
        return ResponseEntity.ok().body(new Response("success", "pay-success", "Payment success"));
    }

//    @GetMapping("/test")
//    public ResponseEntity<?> test(@RequestBody UserDTO userDTO) {
//        try {
//            String token = userService.login(userDTO.getUsername(), userDTO.getPassword());
//
//            User user = userService.getUserDetailFromToken(token);
//
//            tokenService.addToken(user, token);
//
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//        return ResponseEntity.ok().build();
//
//    }

//    @PostMapping(consumes = {"multipart/form-data"})
//    @OpenAPI30
//    public ResponseEntity<?> uploadImage(
//            @RequestPart("image") MultipartFile file,
//            @RequestPart("info") String name
//    ) {
//        Map<String, String> data = this.cloudinaryService.upload(file);
//        System.out.println(name);
//        return ResponseEntity.ok().body(data);
//    }



    @PostMapping("/notification")
    public ResponseEntity<?> sendNotificationByToken(@RequestBody NotificationMessageDTO notificationMessageDTO) {
        return ResponseEntity.ok().body(firebaseMessagingService.sendNotificationByToken(notificationMessageDTO));
    }


}
