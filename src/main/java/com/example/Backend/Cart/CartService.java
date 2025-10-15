package com.example.Backend.Cart;

import com.example.Backend.Product.Product;
import com.example.Backend.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public List<Product> getProductsFromCart(Cart cart) {
        return cart.getProducts();
    }

    public Cart addToCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Product> products = cart.getProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        cart.setProducts(products);

        return cartRepository.save(cart);
    }

    public void removeFromCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Product> products = cart.getProducts();
        products.remove(product);
        cart.setProducts(products);

        cartRepository.save(cart);
    }

    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cartRepository.delete(cart);
    }
}