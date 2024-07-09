package com.example.foodordering.controllers.websetting;

import com.example.foodordering.response.Response;
import com.example.foodordering.services.websetting.WebSettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1.prefix}/web-settings")
@RequiredArgsConstructor
@Tag(name = "WebSettingController", description = "For landing page settings")
public class WebSettingController {
    private final WebSettingService webSettingService;

    @GetMapping
    public ResponseEntity<Response> getWebSettings() {
        return ResponseEntity.ok().body(new Response("success", "Web settings retrieved successfully", webSettingService.getWebSetting()));
    }
}
