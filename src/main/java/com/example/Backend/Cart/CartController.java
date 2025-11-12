package com.example.Backend.Cart;

import com.example.Backend.Product.Product;
import com.example.Backend.visitor.PriceVisitor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/calculate/{cartId}")
    public ResponseEntity<Map<String, Object>> calculatePriceVisitor(@PathVariable Long cartId) {

        Cart cart = cartService.getCartById(cartId);
        List<Product> products = cartService.getProductsFromCart(cart);
        PriceVisitor priceVisitor = new PriceVisitor();
        for (Product product : products) {
            product.accept(priceVisitor);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("originalPrice", priceVisitor.getTotalPrice());
        response.put("discount", priceVisitor.getTotalDiscount());
        response.put("finalPrice", priceVisitor.getResult());

        return ResponseEntity.ok(response);
    }
}