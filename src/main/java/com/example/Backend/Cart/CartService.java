package com.example.Backend.Cart;

import com.example.Backend.Product.Product;
import com.example.Backend.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Product> products = cart.getProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        if (!products.contains(product)) {
            products.add(product);
        }
        if (!product.getCarts().contains(cart)) {
            product.getCarts().add(cart);
        }
        cart.setQuantity(products.size());
        cart.setTotalPrice(calculateCartTotal(products));
        cartRepository.save(cart);
        productRepository.save(product);
        return cart;
    }

    public void removeFromCart(Long id, Long productId) {
        Cart cart = getCartById(id);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Product> products = cart.getProducts();
        products.remove(product);
        product.getCarts().remove(cart);

        cart.setQuantity(products.size());
        cart.setTotalPrice(calculateCartTotal(products));

        cartRepository.save(cart);
        productRepository.save(product);
    }
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        List<Product> products = cart.getProducts();
        for (Product product : products) {
            product.getCarts().remove(cart);
            productRepository.save(product);
        }products.clear();
        cart.setQuantity(0);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
    private Double calculateCartTotal(List<Product> products) {
        double total = 0.0;
        for (Product product : products) {
            total += Double.parseDouble(product.getPrice().toString());
        }
        return total;
    }

}