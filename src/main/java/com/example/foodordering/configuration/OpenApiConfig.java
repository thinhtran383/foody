package com.example.foodordering.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {
                @Server(url = "https://api.thinhtran.online", description = "Server URL for production"),
                @Server(url = "http://localhost:8080", description = "Server URL for local development")
        }
)
@Configuration

public class OpenApiConfig {
}
