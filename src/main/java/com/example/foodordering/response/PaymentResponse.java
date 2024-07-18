package com.example.foodordering.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Data
@Getter
@Setter
@Builder
public class PaymentResponse {
    private int orderId;
    private BigDecimal totalMoney;
}
