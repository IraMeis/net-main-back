package com.morena.netMain.logic.repository.base;

import com.morena.netMain.logic.entity.base.BaseModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseModelEntityRepository<T extends BaseModelEntity, ID> extends JpaRepository<T, ID> {

    List<T> findAllByIsDeletedFalse();

    List<T> findAllByIsDeletedTrue();

    Optional<T> findByUniqueIdAndIsDeletedFalse(Long id);

    Optional<T> findByUuidAndIsDeletedFalse(UUID uuid);

    @Override
    @Modifying
    @Query(value = "update #{#entityName} f set f.isDeleted=true where f.unique_id= :id",nativeQuery = true)
    void deleteById(ID id);

    @Override
    @Modifying
    @Query(value = "update #{#entityName} f set f.isDeleted=true where f= :entity", nativeQuery = true)
    void delete(@Param("entity") T entity);

    @Override
    @Modifying
    @Query(value = "update #{#entityName} f set f.isDeleted=true where f in :entities", nativeQuery = true)
    void deleteAll(Iterable<? extends T> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
