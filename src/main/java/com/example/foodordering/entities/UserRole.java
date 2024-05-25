package com.example.foodordering.entities;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userRole", schema = "foody")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @MapsId("roleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

}