package com.morena.netMain.logic.repository.base;

import com.morena.netMain.logic.entity.base.BaseModelDictionaryEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseModelDictionaryEntityRepository<T extends BaseModelDictionaryEntity,ID> extends BaseModelEntityRepository<T, ID> {

    Optional<T> findByCodeAndIsDeletedIsFalse (Long code);

    Optional<T> findByNameAndIsDeletedIsFalse (String name);
}
