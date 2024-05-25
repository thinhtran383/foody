package com.example.foodordering.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@jakarta.persistence.Table(name = "tables", schema = "foody")
public class Table {
    @Id
    @Column(name = "tableId", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "status")
    private String status;

}