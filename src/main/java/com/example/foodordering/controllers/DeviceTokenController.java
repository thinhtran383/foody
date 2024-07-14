package com.example.foodordering.controllers;

import com.example.foodordering.response.Response;
import com.example.foodordering.services.fcm.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1.prefix}/device-tokens")
@RequiredArgsConstructor
public class DeviceTokenController {
    private final DeviceTokenService deviceTokenService;

    @PostMapping
    public ResponseEntity<Response> saveDeviceToken(@RequestParam String token) {
        return ResponseEntity.ok().body(new Response("success", "Device token saved successfully", deviceTokenService.saveDeviceToken(token)));
    }
}
