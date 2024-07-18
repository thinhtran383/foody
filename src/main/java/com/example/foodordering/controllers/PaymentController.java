package com.example.foodordering.controllers;

import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.response.PaymentResponse;
import com.example.foodordering.response.Response;
import com.example.foodordering.services.OrderService;
import com.example.foodordering.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("${api.v1.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

//    @GetMapping("/momo")
//    public String payWithMoMo(
//    ) {
//
//        String orderId = RandomStringUtils.randomAlphanumeric(10);
//        long amount = 1000;
//        String redirectUrl = "https://api.thinhtran.online/home/pay-success";
//        String ipnUrl = "https://api.thinhtran.online/";
//
//        return paymentService.payWithMoMo(orderId, amount, redirectUrl, ipnUrl);
//    }

    @PostMapping("{tableId}")
    public ResponseEntity<?> pay(
            @PathVariable int tableId,
            @RequestParam(defaultValue = "false", required = false)
            boolean isMoMo
    ) throws DataNotFoundException {


        if(isMoMo){
            PaymentResponse paymentResponse = orderService.paymentOrder(tableId);
            String orderId = String.valueOf(paymentResponse.getOrderId());
            long amount = paymentResponse.getTotalMoney().longValue();

            String redirectUrl = "https://api.thinhtran.online/home/pay-success";
            String ipnUrl = "https://api.thinhtran.online/";

            return ResponseEntity.ok().body(paymentService.payWithMoMo(orderId, amount, redirectUrl, ipnUrl));
        } else {
            orderService.paymentOrder(tableId);
            return ResponseEntity.ok().body(new Response("success", "pay-success", "Payment success"));
        }
    }

}
