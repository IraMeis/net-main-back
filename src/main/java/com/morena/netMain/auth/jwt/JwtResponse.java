package com.morena.netMain.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtResponse {

    private String error;
    private final static String type = "Bearer";
    private String accessToken;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}