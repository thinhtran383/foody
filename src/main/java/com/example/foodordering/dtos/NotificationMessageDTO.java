package com.example.foodordering.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class NotificationMessageDTO {
    @Hidden
    private String recipientToken;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Body is required")
    private String body;


    private String image;

    @Schema(hidden = true)
    @Hidden
    private Map<String, String> data;
}
