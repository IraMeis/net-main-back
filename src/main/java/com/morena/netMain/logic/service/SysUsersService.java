package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.pojo.PSysUsers;
import com.morena.netMain.logic.repository.SysUsersRepository;
import com.morena.netMain.logic.utils.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SysUsersService {

    private final AuthService authService;
    private final SysUsersRepository sysUsersRepository;

    public Optional<SysUsers> getCurrentUser(){
        return sysUsersRepository.findByUniqueIdAndIsDeletedFalse(authService.getCurrentUserId());
    }

    private PSysUsers makeForUser (SysUsers sysUsers){
        return PSysUsers.builder()
                .login(sysUsers.getLogin())
                .about(sysUsers.getAbout())
                .scope(Pair.builder()
                        .label(sysUsers.getScope().getName())
                        .value(sysUsers.getScope().getCode())
                        .build())
                .build();
    }

    private PSysUsers makeForAdmin(SysUsers sysUsers){
        return PSysUsers.builder()
                .uniqueId(sysUsers.getUniqueId())
                .uuid(sysUsers.getUuid())
                .login(sysUsers.getLogin())
                //.password(sysUsers.getPassword())
                .about(sysUsers.getAbout())
                .scope(Pair.builder()
                        .label(sysUsers.getScope().getName())
                        .value(sysUsers.getScope().getCode())
                        .build())
                .build();
    }

    public PSysUsers getUserInvRole (){

        return null;
    }
}