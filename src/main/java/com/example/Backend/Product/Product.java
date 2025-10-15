package com.example.Backend.Product;

import com.example.Backend.Cart.Cart;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product{
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String category;
    @Column
    @NotNull
    private String imageUrl;
    @Column
    @NotNull
    private String description;
    @Column
    private Double price;
    @Id
    @NotNull
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
