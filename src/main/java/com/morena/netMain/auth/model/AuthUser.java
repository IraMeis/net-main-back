package com.morena.netMain.auth.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class AuthUser {

    private Long id;
    private String login;
    private String password;
    private Collection<Role> roles;
}
