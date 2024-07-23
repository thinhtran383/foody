package com.example.foodordering.controllers;

import com.example.foodordering.exceptions.DataNotFoundException;
import com.example.foodordering.response.PaymentResponse;
import com.example.foodordering.response.Response;
import com.example.foodordering.services.OrderService;
import com.example.foodordering.services.PaymentService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("${api.v1.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    @Value("${redirectUrl}")
    private String redirectUrl;

    @Value("${ipUrl}")
    private String ipUrl;


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


        if (isMoMo) {
            PaymentResponse paymentResponse = orderService.getPaymentInfo(tableId);
            String orderId = String.valueOf(paymentResponse.getOrderId());
            long amount = paymentResponse.getTotalMoney().longValue();


            String sb = ipUrl +
                    String.format("/%s", tableId);



            return ResponseEntity.ok().body(new Response("success", "", paymentService.payWithMoMo(orderId, amount, redirectUrl, sb)));
        } else {
            orderService.paymentOrder(tableId);
            return ResponseEntity.ok().body(new Response("success", "pay-success", "Payment success"));
        }
    }

    @Hidden
    @PostMapping("/momo/{tableId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private String payWithMoMo(@PathVariable int tableId) throws DataNotFoundException {
        return orderService.paymentOrder(tableId).toString();
    }

}
