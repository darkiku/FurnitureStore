package com.example.Backend.Cart;

import com.example.Backend.Order.Order;
import com.example.Backend.Product.Product;
import com.example.Backend.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer quantity;
    @Column
    private Double totalPrice;
    @ManyToMany(mappedBy ="carts",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
    @Column
    private String deliveryMethod;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    public Cart(){
        this.products = new ArrayList<>();
    }
}
