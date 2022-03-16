package com.morena.netMain.logic.repository.base;

import com.morena.netMain.logic.entity.base.BaseModelView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseModelViewRepository <T extends BaseModelView, ID> extends JpaRepository<T, ID> {

    Optional<T> findOneByUniqueId(Long id);

    Optional<T> findOneByUuid(UUID uuid);
}
