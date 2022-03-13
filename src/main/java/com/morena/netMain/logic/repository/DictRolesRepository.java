package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictRoles;
import com.morena.netMain.logic.repository.base.BaseModelDictionaryEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface DictRolesRepository extends BaseModelDictionaryEntityRepository<DictRoles,Long> {

    Set<DictRoles> findAllByNameIn (Collection<String> names);
}
