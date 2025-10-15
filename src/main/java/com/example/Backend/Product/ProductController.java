package com.example.Backend.Product;

import ch.qos.logback.core.model.Model;
import com.example.Backend.User.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    private UserService wuserService;
    @GetMapping("/product{id}")
    public ResponseEntity<Product> getProduct(Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        if (products!=null) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Product> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        productService.saveProduct(product);
        return ResponseEntity.ok(product);
    }
}
