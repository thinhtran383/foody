package com.example.foodordering.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "orderDetails", schema = "foody")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetailId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    @ToString.Exclude
    private MenuItem item;

    @Column(name = "quantity")
    private Integer quantity;

}