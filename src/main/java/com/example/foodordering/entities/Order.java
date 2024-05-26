package com.example.foodordering.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@jakarta.persistence.Table(name = "orders", schema = "foody")
@NoArgsConstructor
@AllArgsConstructor

@NamedEntityGraph(
        name = "orderWithDetails",
        attributeNodes = {
                @NamedAttributeNode("orderDetails"),
        }
)
public class Order {
    @Id
    @Column(name = "orderId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableId")
    private Table table;

    @Column(name = "createdTime")
    private Instant createdTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();


    @PrePersist
    public void generateRandomOrderId() {
        this.id = (int) (Math.random() * 1000000);
    }


    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }
}