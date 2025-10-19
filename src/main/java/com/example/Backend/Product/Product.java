package com.example.Backend.Product;

import com.example.Backend.Cart.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cart_product", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    @JsonIgnore
    private List<Cart> carts;
}
