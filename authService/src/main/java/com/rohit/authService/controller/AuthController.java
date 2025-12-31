package com.rohit.authService.controller;

import com.rohit.authService.model.LoginRequest;
import com.rohit.authService.model.LoginResponse;
import com.rohit.authService.model.RefreshRequest;
import com.rohit.authService.model.RefreshToken;
import com.rohit.authService.service.AuthService;
import com.rohit.authService.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse>refresh(@RequestBody RefreshRequest refreshRequest){
        RefreshToken stored=authService.validateRefreshToken(refreshRequest.getRefreshToken());
        String newAccessToken=jwtUtil.generateToken(stored.getUsername(), "PUBLIC");

        return ResponseEntity.ok(
                new LoginResponse(newAccessToken, refreshRequest.getRefreshToken())
        );
    }
}
