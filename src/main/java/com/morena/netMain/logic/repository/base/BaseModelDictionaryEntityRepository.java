package com.morena.netMain.logic.repository.base;

import com.morena.netMain.logic.entity.base.BaseModelDictionaryEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseModelDictionaryEntityRepository<T extends BaseModelDictionaryEntity,ID> extends BaseModelEntityRepository<T, ID> {

    T findOneByCodeAndIsDeletedIsFalse(Long code);

    T findByNameAndIsDeletedIsFalse (String name);
}
