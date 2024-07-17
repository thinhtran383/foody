package com.example.foodordering.response.orders;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@ToString
public class OrderResponse {
    private int tableId;
    private BigDecimal totalMoney;

    @JsonAlias("orderDetails")
    private List<OrderDetailResponse> orderDetails;
}
