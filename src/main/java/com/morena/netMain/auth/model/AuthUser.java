package com.morena.netMain.auth.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class AuthUser {

    private Long id;
    private String login;
    private String password;
    private Collection<Role> roles;
}
