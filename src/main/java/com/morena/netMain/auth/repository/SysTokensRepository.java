package com.morena.netMain.auth.repository;

import com.morena.netMain.auth.model.SysTokens;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SysTokensRepository extends BaseModelEntityRepository<SysTokens, Long> {

    Optional<SysTokens> findByOwnerAndIsDeletedFalse(SysUsers user);
    Optional<SysTokens> findByOwnerAndIsDeletedTrue(SysUsers user);
    Optional<SysTokens> findByOwner(SysUsers user);

    @Override
    @Modifying
    @Query("update SysTokens nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);
}
