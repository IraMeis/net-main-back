package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictScopes;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SysUsersRepository extends BaseModelEntityRepository<SysUsers, Long> {
    Optional<SysUsers> findByLoginAndIsDeletedFalse(String login);

    List<SysUsers> findAllByIsDeletedAndScopeInAndCreatedTimestampBetweenAndLoginContainingAndAboutContainingAndIsUserTrueOrderByLogin(
            Boolean isDeleted,
            Iterable<DictScopes> scope,
            LocalDateTime fromTime, LocalDateTime toTime,
            String loginLabel,
            String aboutLabel);

    List<SysUsers> findAllByIsUserFalseOrderByLogin();
}