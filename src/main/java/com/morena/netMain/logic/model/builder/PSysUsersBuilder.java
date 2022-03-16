package com.morena.netMain.logic.model.builder;

import com.morena.netMain.logic.entity.DictRoles;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.model.PSysUsers;
import com.morena.netMain.logic.utils.Pair;
import com.sun.istack.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PSysUsersBuilder {

    public static PSysUsers toAdminPojo(SysUsers sysUsers){

        return PSysUsers.builder()

                .uniqueId(sysUsers.getUniqueId())
                .uuid(sysUsers.getUuid())
                .createdTimestamp(sysUsers.getCreatedTimestamp())
                .modifiedTimestamp(sysUsers.getModifiedTimestamp())
                .isDeleted(sysUsers.getIsDeleted())

                .about(sysUsers.getAbout())
                .login(sysUsers.getLogin())
                .isActive(sysUsers.getIsActive())
                .isUser(sysUsers.getIsUser())
                .roles(mapRolesToPair(sysUsers.getRawRoles()))
                .scope(Pair.builder()
                        .value(sysUsers.getScope().getCode())
                        .label(sysUsers.getScope().getName())
                        .build())

                .isTokenAllowed(sysUsers.getToken() == null || !sysUsers.getToken().getIsDeleted())

                .build();
    }

    public static PSysUsers toUserPojo(SysUsers sysUsers){

        return PSysUsers.builder()

                .uniqueId(sysUsers.getUniqueId())
                .createdTimestamp(sysUsers.getCreatedTimestamp())

                .about(sysUsers.getAbout())
                .login(sysUsers.getLogin())

                .build();
    }

    private static Collection<Pair> mapRolesToPair (@NotNull Collection<DictRoles> dictRoles){
        return dictRoles.stream()
                .map(dictRolesElem -> Pair.builder()
                        .value(dictRolesElem.getCode())
                        .label(dictRolesElem.getName())
                        .build())
                .collect(Collectors.toSet());
    }

    public static List<PSysUsers> toAdminPojoList(List<SysUsers> users){
        if (users == null)
            return Collections.emptyList();
        return users.stream()
                .map(PSysUsersBuilder::toAdminPojo)
                .collect(Collectors.toList());
    }
}
