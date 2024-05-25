package com.example.foodordering.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@jakarta.persistence.Table(name = "menuItems", schema = "foody")
public class MenuItem {
    @Id
    @Column(name = "itemId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @Size(max = 255)
    @Column(name = "itemName")
    private String itemName;

    @Size(max = 255)
    @Column(name = "image")
    private String image;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

}