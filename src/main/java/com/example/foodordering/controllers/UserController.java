package com.example.foodordering.controllers;

import com.example.foodordering.dtos.UserDTO;
import com.example.foodordering.dtos.UserLoginDTO;
import com.example.foodordering.entities.User;
import com.example.foodordering.response.Response;
import com.example.foodordering.services.TokenService;
import com.example.foodordering.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.v1.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        try {
            String token = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());

            User userLogin = userService.getUserDetailFromToken(token);

            tokenService.addToken(userLogin, token);

            return ResponseEntity.ok().body(new Response("success", "Login successful", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody @Valid UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return ResponseEntity.ok().body(new Response("success", "User created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }

    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException validationEx) {
            validationEx.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof ConstraintViolationException constraintViolationEx) {
            constraintViolationEx.getConstraintViolations().forEach((violation) -> {
                String fieldName = "error";
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        return errors;
    }
}
