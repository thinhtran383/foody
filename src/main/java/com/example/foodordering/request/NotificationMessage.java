package com.example.foodordering.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private String image;

    @Schema(hidden = true)
    private Map<String, String> data;
}
