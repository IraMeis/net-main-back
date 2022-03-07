package com.morena.netMain.auth.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshRequest {
    public String refreshToken;
}
