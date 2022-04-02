package com.morena.netMain.auth.jwt;

import com.morena.netMain.auth.model.AuthUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class LoginResponse {
    private Long id;
    private String username;
    //private String email;
    private Set<String> roles;
    private String accessToken;
    private String refreshToken;
    //private String type = "Bearer";

    public LoginResponse(AuthUser authUser, String accessToken, String refreshToken){
        this.id = authUser.getId();
        this.username = authUser.getLogin();
        this.roles = authUser.getRoles()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
