package com.example.foodordering.controllers;

import com.example.foodordering.dtos.NotificationMessageDTO;
import com.example.foodordering.response.Response;
import com.example.foodordering.services.fcm.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1.prefix}/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final FirebaseMessagingService firebaseMessagingService;
    @PostMapping("/send")
    public ResponseEntity<Response> sendNotification(@RequestBody NotificationMessageDTO notificationMessageDTO) {
        return ResponseEntity.ok().body(
                new Response("success",
                        "Notification sent successfully",
                        firebaseMessagingService.sendNotificationToAll(notificationMessageDTO)
                ));
    }
}
