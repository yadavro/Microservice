package com.rohit.authService.service;

import com.rohit.authService.model.LoginRequest;
import com.rohit.authService.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequest request) {
        // 👇 Fake user validation (replace with DB validation)
        if (request.getUsername().equals("rohit")
                && request.getPassword().equals("password123")) {

            return jwtUtil.generateToken(request.getUsername(),"PUBLIC");
        }

        throw new RuntimeException("Invalid username or password");
    }
}
