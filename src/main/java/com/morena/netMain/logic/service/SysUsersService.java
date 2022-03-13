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

    public Optional<SysUsers> getUserById(Long id){
        return sysUsersRepository.findByUniqueIdAndIsDeletedFalse(id);
    }
}