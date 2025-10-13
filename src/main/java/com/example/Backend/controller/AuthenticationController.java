package com.example.Backend.controller;

import com.example.Backend.Model.User;
import com.example.Backend.Service.AuthenticationService;
import com.example.Backend.Service.JwtService;
import com.example.Backend.Validation.LoginUserDto;
import com.example.Backend.Validation.RegisterUserDto;
import com.example.Backend.Validation.VerifyUserDto;
import com.example.Backend.responses.LoginResponse;
import jakarta.servlet.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser.toString());
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDto) {
        User loginedUser = authenticationService.signIn(loginUserDto);
        String token = jwtService.generateToken(loginedUser);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse.toString());
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyUserDto verifyUserDto) {
        try{authenticationService.verifyUser(verifyUserDto);
        return ResponseEntity.ok(verifyUserDto.toString());}
    catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }}
    @PostMapping("/resend")
    public ResponseEntity<String> resend(@RequestParam String email) {
        try{authenticationService.resendVerificationEmail(email);
        return ResponseEntity.ok("Verification code has been sent");}
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
