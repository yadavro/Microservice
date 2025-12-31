package com.rohit.authService.service;

import com.rohit.authService.model.LoginRequest;
import com.rohit.authService.model.LoginResponse;
import com.rohit.authService.model.RefreshToken;
import com.rohit.authService.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Autowired
    private JwtUtil jwtUtil;

    private Map<String, RefreshToken> refreshStore = new HashMap<>();

    public LoginResponse login(LoginRequest request) {
        if (!request.getUsername().equals("rohit") ||
                !request.getPassword().equals("password123")) {
            throw new RuntimeException("Invalid Credentials!");
        }
        String accessToken = jwtUtil.generateToken(
                request.getUsername(), "PUBLIC");

        String refreshTokenValue = jwtUtil.generateRefreshToken();

        RefreshToken refeshToken = new RefreshToken(
                refreshTokenValue,
                request.getUsername(),
                Instant.now().plusSeconds(refreshExpiration));

        refreshStore.put(refreshTokenValue,refeshToken);

        return new LoginResponse(accessToken,refreshTokenValue);
    }

    public RefreshToken validateRefreshToken(String token){
        RefreshToken refreshToken=refreshStore.get(token);
        if(refreshToken==null){
            throw new RuntimeException("Invalid refresh token");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshStore.remove(token);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
