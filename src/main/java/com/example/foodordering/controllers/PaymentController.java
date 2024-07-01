package com.example.foodordering.controllers;

import com.example.foodordering.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("${api.v1.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public String payWithMoMo(
    ) {

        String orderId = RandomStringUtils.randomAlphanumeric(10);
        long amount = 1000;
        String redirectUrl = "http://localhost:8080";
        String ipnUrl = "http://localhost:8080/done";

        return paymentService.payWithMoMo(orderId, amount, redirectUrl, ipnUrl);
    }
}
