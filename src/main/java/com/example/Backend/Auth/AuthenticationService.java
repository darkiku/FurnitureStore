package com.example.Backend.Auth;

import com.example.Backend.Cart.Cart;
import com.example.Backend.Cart.CartRepository;
import com.example.Backend.User.User;
import com.example.Backend.User.UserRepository;
import com.example.Backend.dto.LoginUserDto;
import com.example.Backend.dto.RegisterUserDto;
import com.example.Backend.dto.VerifyUserDto;
import jakarta.mail.MessagingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Data
@Service
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final CartRepository cartRepository;
    private final AuthenticationManager authenticationManager;
    public AuthenticationService(CartRepository cartRepository, UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.cartRepository = cartRepository;
    }
    public User signUp(RegisterUserDto input) {
        User user = new User(
                input.getUsername(),
                passwordEncoder.encode(input.getPassword()),
                input.getEmail()
        );
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpireAtf(LocalDateTime.now().plusMinutes(30));
        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cart.setProducts(new ArrayList<>());
        cart.setQuantity(0);
        cart.setTotalPrice(0.0);
        cart.setDeliveryMethod("Yandex");
        cartRepository.save(cart);
        sendVerificationEmail(user);
        return savedUser;
    }

    public User signIn(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified, pls verify account");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );

        return user;
    }

    public void verifyUser(VerifyUserDto input) {
        Optional<User> userOptional = userRepository.findByEmail(input.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getVerificationExpireAtf().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Code is expired");
            }

            if (user.getVerificationCode().equals(input.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationExpireAtf(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void resendVerificationEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExpireAtf(LocalDateTime.now().plusMinutes(30));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void sendVerificationEmail(User user) {
        String subject = "Account verification code";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to Furni</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }
}