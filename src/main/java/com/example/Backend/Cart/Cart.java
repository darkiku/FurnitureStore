package com.example.Backend.Cart;

import com.example.Backend.Order.Order;
import com.example.Backend.Product.Product;
import com.example.Backend.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @Column
    private Integer quantity;
    @Column
    private double totalPrice;
    @OneToMany(mappedBy ="cart",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
    @Column
    @NotNull
    private String deliveryMethod;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    public Cart(){
        this.products = new ArrayList<>();
    }
}
