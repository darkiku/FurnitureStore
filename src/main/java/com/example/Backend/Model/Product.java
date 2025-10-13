//package com.example.Backend.Model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import java.util.List;
//import java.util.UUID;
//
//@Getter
//@Setter
//@ToString
//@Entity
//@Table(name = "products")
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, unique = true)
//    private UUID id;
//
//    @Column(nullable = false, unique = true)
//    private String slug;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(name = "price_from")
//    private Double priceFrom;
//
//    @Column(name = "in_stock")
//    private Boolean inStock;
//
//    @Column(name= "out_stock")
//    private Boolean outStock;
//
//    @ElementCollection
//    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
//    @Column(name = "image_url")
//    private List<String> images;
//
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<P> attributes;
//
//    @Column(columnDefinition = "TEXT")
//    private String delivery;
//
//    @Column(columnDefinition = "TEXT")
//    private String payment;
//
//    @ElementCollection
//    @CollectionTable(name = "product_badges", joinColumns = @JoinColumn(name = "product_id"))
//    @Column(name = "badge")
//    private List<String> badges;
//}
