package com.example.foodordering.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@jakarta.persistence.Table(name = "orderDetails", schema = "foody")
public class OrderDetail {
    @Id
    @Column(name = "orderDetailId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private MenuItem item;

    @Column(name = "quantity")
    private Integer quantity;

}