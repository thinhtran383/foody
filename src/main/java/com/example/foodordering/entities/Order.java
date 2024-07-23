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
@ToString
@jakarta.persistence.Table(name = "orders", schema = "foody")
@NoArgsConstructor
@AllArgsConstructor

@NamedEntityGraph(
        name = "orderWithDetailsAndItems",
        attributeNodes = {
                @NamedAttributeNode(value = "orderDetails", subgraph = "orderDetails-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "orderDetails-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("item")
                        }
                )
        }
)
public class Order {
    @Id
    @Column(name = "orderId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableId")
    @ToString.Exclude
    private Table table;

    @Column(name = "createdTime")
    private Instant createdTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();


    @PrePersist
    public void generateRandomOrderId() {
        this.id = (int) (Math.random() * 1000000);
    }

}