package com.example.foodordering.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Random;

@Getter
@Setter
@Entity
@jakarta.persistence.Table(name = "orders", schema = "foody")
public class Order {
    @Id
    @Column(name = "orderId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableId")
    private Table table;

    @Column(name = "createdTime")
    private Instant createdTime;

    @PrePersist
    public void generateRandomOrderId() {
        this.id = new Random().nextInt(1000000);
    }
}