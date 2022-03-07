package com.morena.netMain.auth.controller;

import com.morena.netMain.auth.jwt.JwtRefreshRequest;
import com.morena.netMain.auth.jwt.JwtRequest;
import com.morena.netMain.auth.jwt.JwtResponse;
import com.morena.netMain.auth.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthTokenService authTokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authTokenService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRefreshRequest request) {
        final JwtResponse token = authTokenService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRefreshRequest request) {
        final JwtResponse token = authTokenService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
