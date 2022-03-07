package com.morena.netMain.auth.repository;

import com.morena.netMain.auth.model.SysTokens;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;

import java.util.Optional;

public interface SysTokensRepository extends BaseModelEntityRepository<SysTokens, Long> {

    Optional<SysTokens> findByOwnerAndIsDeletedFalse(SysUsers user);
    Optional<SysTokens> findByOwnerAndIsDeletedTrue(SysUsers user);
    Optional<SysTokens> findByOwner(SysUsers user);
}
