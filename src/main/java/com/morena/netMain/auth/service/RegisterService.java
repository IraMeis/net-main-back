package com.morena.netMain.auth.service;

import com.morena.netMain.auth.model.RegisterUser;
import com.morena.netMain.auth.model.Role;
import com.morena.netMain.auth.repository.AuthUserRepository;
import com.morena.netMain.logic.entity.DictRoles;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.DictRolesRepository;
import com.morena.netMain.logic.repository.DictScopesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final AuthUserRepository authUserRepository;
    private final DictScopesRepository dictScopesRepository;
    private final DictRolesRepository dictRolesRepository;

    public boolean saveUser (RegisterUser user){
        SysUsers savingUser = mapToSysUser(user);
        if(savingUser == null)
            return false;

        authUserRepository.save(savingUser);
        return true;
    }


    public SysUsers mapToSysUser(RegisterUser registerUser){

        if(authUserRepository.existsByLogin(registerUser.getUsername()))
            return null;

        SysUsers sysUser = new SysUsers();
        sysUser.setUuid(UUID.randomUUID());
        sysUser.setIsDeleted(false);

        sysUser.setLogin(registerUser.getUsername());
        sysUser.setPassword(registerUser.getPassword());
      //  sysUser.setAbout(registerUser.getAbout());

        sysUser.setIsUser(true);
        sysUser.setIsActive(false);
        sysUser.setRawRoles(mapToRawRoles(Set.of(
                comment_viewer,
                comment_modifier,
                post_viewer,
                user_data_viewer,
                user_data_modifier)));
        sysUser.setScope(dictScopesRepository.findTopByIsDeletedFalseOrderByCodeDesc());
        return sysUser;
    }

    private Collection<DictRoles> mapToRawRoles(Collection<Role> roles){
        return dictRolesRepository.findAllByNameIn(roles.stream()
                .map(Enum::name)
                .collect(Collectors.toSet()));
    }
}