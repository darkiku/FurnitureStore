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

    @GetMapping("/showCart/{cartId}")
    public ResponseEntity<List<Product>> showCart(@PathVariable Long cartId){
        Cart cart = cartService.getCartById(cartId);
        List<Product> products = cartService.getProductsFromCart(cart);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addToCart/{cartId}/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId, @PathVariable Long productId){
        Cart cart = cartService.addToCart(cartId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @DeleteMapping("/removeFromCart/{cartId}/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        cartService.removeFromCart(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clearCart/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }
}