package com.example.foodordering.controllers;

import com.example.foodordering.dtos.OrderItemDTO;
import com.example.foodordering.entities.Table;
import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.response.Response;
import com.example.foodordering.response.orders.OrderResponse;
import com.example.foodordering.services.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.v1.prefix}/orders")
@RequiredArgsConstructor
@Validated
@Tag(name = "OrderController", description = "Operations pertaining to orders in Food Ordering System")
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    @ApiResponse(content = @Content(schema = @Schema(implementation = OrderItemDTO.class), mediaType = "application/json"))
    public ResponseEntity<Response> createOrder(@Valid @RequestParam int tableId, @Valid @RequestBody List<OrderItemDTO> orderDetails) throws Exception {


        return ResponseEntity.ok().body(
                new Response("success",
                        "Retrieved successfully",
                        orderService.createOrUpdateOrder(tableId, orderDetails))
        );

    }

    @GetMapping("/getOrderDetails")
    public ResponseEntity<Response> getOrderDetails(@Valid @RequestParam int tableId) throws DataNotFoundException {
        return ResponseEntity.ok().body(
                new Response("success",
                        "Retrieved successfully",
                        orderService.getAllOrderByTableId(tableId))
        );
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
