package com.example.foodordering.entities;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

import static org.apache.catalina.realm.UserDatabaseRealm.getRoles;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "userWithRoles",
        attributeNodes = {
                @NamedAttributeNode(value = "roles")
        }
)



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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Token> tokens = new LinkedHashSet<>();

    @Size(max = 255)
    @Column(name = "fullname")
    private String fullname;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 11)
    @Column(name = "phoneNumber", length = 11)
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(getRoles().getRoleName().toUpperCase()));
        return authorityList;
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