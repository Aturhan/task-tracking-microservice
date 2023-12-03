package com.abdullahturhan.controller;

import com.abdullahturhan.dto.AuthRequest;

import com.abdullahturhan.dto.CreateUserRequest;
import com.abdullahturhan.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody CreateUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest request) {
        return authService.generateToken(request);
    }
    @GetMapping("/validate")
    public String validate(@RequestParam("token") String token){
        authService.validateToken(token);
        return "token is valid";
    }

}
