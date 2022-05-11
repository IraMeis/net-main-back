package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictScopes;
import com.morena.netMain.logic.repository.base.BaseModelDictionaryEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictScopesRepository extends BaseModelDictionaryEntityRepository<DictScopes, Long> {

    DictScopes findTopByIsDeletedFalseOrderByCodeDesc();

    @Override
    @Modifying
    @Query("update DictScopes nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);
}
