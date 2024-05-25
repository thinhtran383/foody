package com.example.foodordering.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "menuItems", schema = "foody")
@NamedEntityGraph(
        name = "menuItemWithCategory",
        attributeNodes = {
                @NamedAttributeNode("category"),
        }
)
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

}