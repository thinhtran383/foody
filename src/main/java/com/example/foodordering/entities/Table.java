package com.example.foodordering.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@jakarta.persistence.Table(name = "tables", schema = "foody")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tableId", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "table")
    private Set<Order> orders = new LinkedHashSet<>();

}