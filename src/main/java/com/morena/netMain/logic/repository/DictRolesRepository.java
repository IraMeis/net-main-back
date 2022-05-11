package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictRoles;
import com.morena.netMain.logic.repository.base.BaseModelDictionaryEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface DictRolesRepository extends BaseModelDictionaryEntityRepository<DictRoles, Long> {

    Set<DictRoles> findAllByNameIn (Collection<String> names);

    @Override
    @Modifying
    @Query("update DictRoles nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);
}
