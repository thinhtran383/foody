package com.example.foodordering.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles", schema = "foody")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId", nullable = false)
    @JsonIgnore
    private Integer id;

    @Size(max = 255)
    @Column(name = "roleName")
    private String roleName;


    @Override
    public String toString() {
        return getRoleName();
    }
}