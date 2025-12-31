package com.rohit.authService.model;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
