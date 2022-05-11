package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SysUsersRepository extends BaseModelEntityRepository<SysUsers, Long>, QuerydslPredicateExecutor<SysUsers> {

    Optional<SysUsers> findOneByLoginAndIsDeletedFalse(String login);

    List<SysUsers> findAllByOrderByLogin();

    @Query("select max(u.createdTimestamp) from SysUsers u")
    LocalDateTime findMaxDate();

    @Query("select min(u.createdTimestamp) from SysUsers u")
    LocalDateTime findMinDate();

    @Override
    @Modifying
    @Query("update SysUsers nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);
}