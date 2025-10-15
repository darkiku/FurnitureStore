package com.example.Backend.User;

import com.example.Backend.Cart.Cart;
import com.example.Backend.Order.Order;
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
    private  String password;
    @Column(unique = true,nullable = false)
    private String email;
    private boolean enabled;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    private LocalDateTime verificationExpireAtf;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;



    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public User(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
