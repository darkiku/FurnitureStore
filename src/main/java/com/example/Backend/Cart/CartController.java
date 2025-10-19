package com.example.Backend.Cart;

import com.example.Backend.Product.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Transactional
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/showCart/{id}")
    public ResponseEntity<List<Product>> showCart(@PathVariable Long id){
        Cart cart = cartService.getCartById(id);
        List<Product> products = cartService.getProductsFromCart(cart);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addToCart/{cartId}/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId, @PathVariable Long productId){
        Cart cart = cartService.addToCart(cartId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @DeleteMapping("/removeFromCart/{id}/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id, @PathVariable Long productId){
        cartService.removeFromCart(id, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clearCart/{id}")
    public ResponseEntity<Void> clearCart(@PathVariable Long id){
        cartService.clearCart(id);
        return ResponseEntity.noContent().build();
    }
}