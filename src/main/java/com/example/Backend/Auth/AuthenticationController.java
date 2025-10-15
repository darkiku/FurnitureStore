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
        User registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser.toString());
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User loginedUser = authenticationService.signIn(loginUserDto);
        String token = jwtService.generateToken(loginedUser);
        LoginResponse loginResponse = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
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
