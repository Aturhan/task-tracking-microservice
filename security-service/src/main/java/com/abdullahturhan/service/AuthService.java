package com.abdullahturhan.service;

import com.abdullahturhan.dto.AuthRequest;
import com.abdullahturhan.dto.CreateUserRequest;
import com.abdullahturhan.dto.CreateUserResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtils utils;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;

    public AuthService(RestTemplate restTemplate, JwtUtils utils, BCryptPasswordEncoder passwordEncoder, AuthenticationManager manager) {
        this.restTemplate = restTemplate;
        this.utils = utils;
        this.passwordEncoder = passwordEncoder;
        this.manager = manager;
    }

    public String register(CreateUserRequest request){
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            CreateUserResponse user =  restTemplate.postForObject("http://localhost:8081/api/users/register", request, CreateUserResponse.class);
        }catch (Exception e){
            throw new RuntimeException("Exception = "+ e.getMessage());
        }
       return "Registered";
    }

    public String generateToken(AuthRequest request) {
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        if (authenticate.isAuthenticated()) {
            return utils.generateToken(request.email(), request.password());
        }
        throw new RuntimeException("Authentication is failed");
    }

    public void validateToken(String token){
        utils.validateToken(token);
    }
}
