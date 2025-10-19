package com.example.Backend.Auth;

import com.example.Backend.User.User;
import com.example.Backend.dto.LoginUserDto;
import com.example.Backend.dto.RegisterUserDto;
import com.example.Backend.dto.VerifyUserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok("Registration successful! Check your email for verification code.");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);

            User user = authenticationService.getUserByEmail(verifyUserDto.getEmail());
            String token = jwtService.generateToken(user);

            LoginResponse response = new LoginResponse(token, jwtService.getExpirationTime());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            User user = authenticationService.signIn(loginUserDto);
            String token = jwtService.generateToken(user);

            LoginResponse response = new LoginResponse(token, jwtService.getExpirationTime());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resend(@RequestParam String email) {
        try {
            authenticationService.resendVerificationEmail(email);
            return ResponseEntity.ok("Verification code has been sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}