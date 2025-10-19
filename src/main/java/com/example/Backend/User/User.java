package com.example.Backend.User;

import com.example.Backend.Cart.Cart;
import com.example.Backend.Order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@ToString
@Table(name = "users")
@Entity
public class User implements UserDetails {
    private String firstName;
    private String lastName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private  String username;
    @Column(nullable = false)
    @JsonIgnore
    private  String password;
    @Column(unique = true,nullable = false)
    private String email;
    private boolean enabled;
    @JsonIgnore
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    @JsonIgnore
    private LocalDateTime verificationExpireAtf;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cart cart;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;



    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User(){}

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
