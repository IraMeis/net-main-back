package com.morena.netMain.auth.model;

import com.morena.netMain.logic.entity.DictRoles;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static Collection<Role> getRolesFromRawRoles(Collection<DictRoles> raw) {
        return raw.stream()
                .map(rawrole->Role.valueOf(rawrole.getName()))
                .collect(Collectors.toList());
    }
}
