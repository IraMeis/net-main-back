package com.morena.netMain.auth.service;

import com.morena.netMain.auth.model.AuthUser;
import com.morena.netMain.auth.model.RoleMapper;
import com.morena.netMain.auth.repository.AuthUserRepository;
import com.morena.netMain.logic.entity.SysUsers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthUserService {

    private final AuthUserRepository authUserRepository;

    public AuthUser getByLogin(String login){
        Optional<SysUsers> sysUsers = authUserRepository.findByLoginAndIsDeletedFalse(login);
        if(sysUsers.isEmpty()) return null;

        return mapToAuthUser(sysUsers.get());
    }

    public static AuthUser mapToAuthUser(SysUsers sysUsers){
        return AuthUser.builder()
                .id(sysUsers.getUniqueId())
                .login(sysUsers.getLogin())
                .password(sysUsers.getPassword())
                .roles(RoleMapper.getRolesFromRawRoles(sysUsers.getRawRoles()))
                .build();
    }

    public Optional<SysUsers> getSysUserByLogin(String login) {
        return authUserRepository.findByLoginAndIsDeletedFalse(login);
    }

    public void saveSysUser(SysUsers sysUsers){
        authUserRepository.save(sysUsers);
    }
}
