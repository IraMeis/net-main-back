package com.morena.netMain.logic.service;

import com.morena.netMain.auth.jwt.JwtAuthentication;
import com.morena.netMain.auth.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getCurrentUserId (){
        return getAuthInfo().getId();
    }

    public String getCurrentUserLogin (){
        return getAuthInfo().getLogin();
    }

    public Set<Role> getCurrentUserRoles(){
        return getAuthInfo().getRoles();
    }
}
