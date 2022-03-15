package com.morena.netMain.logic.service;

import com.morena.netMain.auth.model.SysTokens;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.pojo.PSysUsers;
import com.morena.netMain.logic.pojo.builder.PSysUsersBuilder;
import com.morena.netMain.logic.repository.SysUsersRepository;
import com.morena.netMain.logic.utils.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class SysUsersService implements RoleChecker{

    private final AuthService authService;
    private final SysUsersRepository sysUsersRepository;

    @Override
    public boolean isAdmin() {
        return authService.getCurrentUserRoles().stream().anyMatch(List.of(
                system,
                user_data_admin_viewer,
                user_data_admin_deleter)
                ::contains);
    }

    public Optional<SysUsers> getCurrentUser(){
        return sysUsersRepository.findOneByUniqueIdAndIsDeletedFalse(authService.getCurrentUserId());
    }

    public Optional<SysUsers> getUserById(Long id){
        return sysUsersRepository.findOneByUniqueIdAndIsDeletedFalse(id);
    }

    public PSysUsers getByIdMapped(Long id){
        Optional<SysUsers> user = getUserById(id);
        if (user.isEmpty())
            return null;

        if(isAdmin())
            return PSysUsersBuilder.adminUserBuild(user.get());
        return PSysUsersBuilder.userUserBuild(user.get());
    }

    public PSysUsers getCurrentMapped(){
        Optional<SysUsers> user = getCurrentUser();
        if (user.isEmpty())
            return null;

        if(isAdmin())
            return PSysUsersBuilder.adminUserBuild(user.get());
        return PSysUsersBuilder.userUserBuild(user.get());
    }

    public boolean updateAdditionalData (Pair userData){
        Optional<SysUsers> sysUsers = sysUsersRepository.findOneByUniqueIdAndIsDeletedFalse(userData.getValue());
        if (sysUsers.isEmpty())
            return false;

        sysUsers.get().setAbout(userData.getLabel());
        sysUsersRepository.save(sysUsers.get());
        return true;
    }

    public void deleteUser(Long id){
        sysUsersRepository.deleteById(id);
    }

    public void banUser(Long id){
        Optional<SysUsers> user = getUserById(id);
        if (user.isEmpty())
            return;

        SysTokens token = user.get().getToken();
        if(token == null)
            return;

        token.setIsDeleted(true);
        sysUsersRepository.save(user.get());
    }
}