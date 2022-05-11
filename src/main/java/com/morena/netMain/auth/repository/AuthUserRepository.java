package com.morena.netMain.auth.repository;

import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends BaseModelEntityRepository<SysUsers, Long> {

    Optional<SysUsers> findByLoginAndIsDeletedFalse(String login);
    Boolean existsByLogin(String login);

    @Override
    @Modifying
    @Query("update SysUsers nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);
}