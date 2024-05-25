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
@Table(name = "users", schema = "foody")
public class User {
    @Id
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "userName")
    private String userName;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

}