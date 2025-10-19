package com.example.Backend.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> authenticatedUser() {
        System.out.println("=== /users/me CALLED ===");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        System.out.println("User from principal: " + user.getEmail());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Has cart: " + (user.getCart() != null));

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("enabled", user.isEnabled());

        if (user.getCart() != null) {
            Map<String, Object> cart = new HashMap<>();
            cart.put("id", user.getCart().getId());
            cart.put("quantity", user.getCart().getQuantity() != null ? user.getCart().getQuantity() : 0);
            cart.put("totalPrice", user.getCart().getTotalPrice());
            response.put("cart", cart);
            System.out.println("Cart: " + cart);
        } else {
            System.out.println("WARNING: No cart!");
        }

        System.out.println("Response: " + response);
        System.out.println("=== RETURNING ===");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}