package com.example.foodordering.controllers.websetting;

import com.example.foodordering.dtos.WebSettingUpdateDTO;
import com.example.foodordering.entities.WebSetting;
import com.example.foodordering.response.Response;
import com.example.foodordering.services.websetting.WebSettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<Response> updateWebSetting(@RequestBody WebSettingUpdateDTO webSetting) {
        webSetting.setId(1L);
        return ResponseEntity.ok().body(new Response("success", "Web settings updated successfully", webSettingService.updateWebSetting(webSetting)));
    }
}
