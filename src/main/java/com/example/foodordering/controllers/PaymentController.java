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

    @GetMapping("/momo")
    public String payWithMoMo(
    ) {

        String orderId = RandomStringUtils.randomAlphanumeric(10);
        long amount = 1000;
        String redirectUrl = "https://api.thinhtran.online/home/pay-success";
        String ipnUrl = "https://api.thinhtran.online/";

        return paymentService.payWithMoMo(orderId, amount, redirectUrl, ipnUrl);
    }

}
