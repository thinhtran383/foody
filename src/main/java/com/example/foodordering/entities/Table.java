package com.example.foodordering.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Table(name = "tables", schema = "foody")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tableId", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "status")
    private String status;

//    @JsonIgnore
//    @OneToMany(mappedBy = "table")
//    private Set<Order> orders = new LinkedHashSet<>();

}