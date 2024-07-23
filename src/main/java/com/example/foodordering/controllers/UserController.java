package com.example.foodordering.controllers;

import com.example.foodordering.dtos.UpdateUserDTO;
import com.example.foodordering.dtos.UserDTO;
import com.example.foodordering.dtos.UserLoginDTO;
import com.example.foodordering.entities.Token;
import com.example.foodordering.entities.User;
import com.example.foodordering.response.user.ListUserResponse;
import com.example.foodordering.response.user.LoginResponse;
import com.example.foodordering.response.Response;
import com.example.foodordering.response.user.UserResponse;
import com.example.foodordering.services.TokenService;
import com.example.foodordering.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.v1.prefix:/api/v1}/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;


    @GetMapping("/all")
    public ResponseEntity<Response> getAllUsers(
            @RequestParam(required = false, defaultValue = "0")
            int page,
            @RequestParam(required = false, defaultValue = "5")
            int limit
    ) {
        Pageable pageable = PageRequest.of(page, limit);

//        Page<UserResponse> userPage = userService.getAllUsers(pageable).map(UserResponse::fromUser);
        Page<UserResponse> userPage = userService.getAllUsers(pageable);

        int totalPages = userPage.getTotalPages();

        ListUserResponse response = ListUserResponse.builder()
                .userResponses(userPage.getContent())
                .totalPages(totalPages)
                .build();

        return ResponseEntity.ok().body(new Response("success", "User retrieved successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(
            @RequestBody @Valid UserLoginDTO userLoginDTO,
            @RequestParam(defaultValue = "false") boolean isAdmin
    ) {

        try {
            String tokenGenerate = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());

            User userLoginDetail = userService.getUserDetailFromToken(tokenGenerate);

            boolean hasRoleAdmin = userLoginDetail.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

            if ((isAdmin && !hasRoleAdmin) || (!isAdmin && hasRoleAdmin)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response("error", "You are not authorized to access this resource", null));

//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Location", "/error/403");
//                return new ResponseEntity<>(headers, HttpStatus.FOUND);

            }

            Token token = tokenService.addToken(userLoginDetail, tokenGenerate); // Save token to database

            LoginResponse loginResponse = modelMapper.map(userLoginDetail, LoginResponse.class);
            loginResponse.setToken(token.getToken());
            loginResponse.setRole(token.getUser()
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

            return ResponseEntity.ok().body(new Response("success", "Login successful", loginResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }


    @PostMapping("/create")
    @Operation(description = "roleID = 1 is for user, roleID = 2 is for admin")
    public ResponseEntity<Response> create(@RequestBody @Valid UserDTO userDTO) {
        try {

            return ResponseEntity.ok().body(new Response("success", "User created successfully", UserResponse.fromUser(userService.createUser(userDTO))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Response> update(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserDTO updateUserDTO
    ) {
        try {
            User userDTOUpdated = userService.updateInfo(userId, updateUserDTO);


            return ResponseEntity.ok().body(new Response("success", "User updated successfully", UserResponse.fromUser(userDTOUpdated)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response> delete(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok().body(new Response("success", "User deleted successfully", UserResponse.fromUser( userService.deleteUserByUserId(userId))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response("error", e.getMessage(), null));
        }
    }



//    @Hidden
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
//    public Map<String, String> handleValidationExceptions(Exception ex) {
//        Map<String, String> errors = new HashMap<>();
//        if (ex instanceof MethodArgumentNotValidException validationEx) {
//            validationEx.getBindingResult().getAllErrors().forEach((error) -> {
//                String fieldName = ((FieldError) error).getField();
//                String errorMessage = error.getDefaultMessage();
//                errors.put(fieldName, errorMessage);
//            });
//        } else if (ex instanceof ConstraintViolationException constraintViolationEx) {
//            constraintViolationEx.getConstraintViolations().forEach((violation) -> {
//                String fieldName = "error";
//                String errorMessage = violation.getMessage();
//                errors.put(fieldName, errorMessage);
//            });
//        }
//        return errors;
//    }
}
