package com.morena.netMain.auth.controller;

import com.morena.netMain.auth.jwt.JwtRefreshRequest;
import com.morena.netMain.auth.jwt.JwtRequest;
import com.morena.netMain.auth.jwt.JwtResponse;
import com.morena.netMain.auth.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthTokenService authTokenService;

    /**
     * /api/auth/login
     * @param authRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authRequest) {
        return authTokenService.login(authRequest).get();
    }

    /**
     * /api/auth/token
     * @param request
     * @return
     */
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRefreshRequest request) {
        final JwtResponse token = authTokenService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * /api/auth/refresh
     * @param request
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRefreshRequest request) {
        final JwtResponse token = authTokenService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
