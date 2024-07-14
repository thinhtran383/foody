package com.example.foodordering.entities;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "userWithUserInfo",
        attributeNodes = {
                @NamedAttributeNode("userInfo"),
                @NamedAttributeNode("roles")
        }
)
@ToString
@Table(name = "users", schema = "foody")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "userName")
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            roles = new LinkedHashSet<>();
        }

        return getRoles().stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRoleName())).toList();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}