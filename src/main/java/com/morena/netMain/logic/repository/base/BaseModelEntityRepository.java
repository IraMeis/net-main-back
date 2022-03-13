package com.morena.netMain.logic.repository.base;

import com.morena.netMain.logic.entity.base.BaseModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseModelEntityRepository<T extends BaseModelEntity, ID> extends JpaRepository<T, ID> {

    List<T> findAllByIsDeletedFalse();

    List<T> findAllByIsDeletedTrue();

    Optional<T> findByUniqueIdAndIsDeletedFalse(Long id);

    Optional<T> findByUniqueId(Long id);

    Optional<T> findByUuidAndIsDeletedFalse(UUID uuid);

    @Override
    @Modifying
    @Query("update NotePosts np set np.isDeleted=true where np.uniqueId=:id")
    void deleteById(ID id);

//    @Override
//    @RestResource(exported = false)
//    void delete(@Param("entity") T entity);

//    @Override
//    @RestResource(exported = false)
//    void deleteAll(Iterable<? extends T> entities);

//    @Override
//    @RestResource(exported = false)
//    void deleteAll();
}
