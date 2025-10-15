package com.example.Backend.Cart;

import com.example.Backend.User.User;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {
    Optional<Cart> findById(Long cartId);
}
