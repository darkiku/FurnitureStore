package com.example.Backend.Order;

import com.example.Backend.Cart.Cart;
import com.example.Backend.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy ="order",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cart> items;
    @Column
    @NotNull
    private Double totalPrice;
    @Column
    @NotNull
    private String status;
}
