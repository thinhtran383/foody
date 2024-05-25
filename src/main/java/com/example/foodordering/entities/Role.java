package com.example.foodordering.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles", schema = "foody")
public class Role {
    @Id
    @Column(name = "roleId", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "roleName")
    private String roleName;

}