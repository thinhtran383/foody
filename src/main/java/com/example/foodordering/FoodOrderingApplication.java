package com.example.foodordering;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "https://api.thinhtran.online/", description = "Default Server URL")})
@SpringBootApplication
public class FoodOrderingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingApplication.class, args);
    }

}
