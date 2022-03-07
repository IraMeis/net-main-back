package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictRoles;
import com.morena.netMain.logic.repository.base.BaseModelDictionaryEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictRolesRepository extends BaseModelDictionaryEntityRepository<DictRoles,Long> {
}
