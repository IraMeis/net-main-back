package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictScopes;
import com.morena.netMain.logic.repository.base.BaseModelDictionaryEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictScopesRepository extends BaseModelDictionaryEntityRepository<DictScopes,Long> {

    DictScopes findTopByOrderByCodeDesc();
}
