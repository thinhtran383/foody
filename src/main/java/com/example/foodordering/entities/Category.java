package com.example.foodordering.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "foody")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "categoryName")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private Set<MenuItem> menuItems = new LinkedHashSet<>();

}