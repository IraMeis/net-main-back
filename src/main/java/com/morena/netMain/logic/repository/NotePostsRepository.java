package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotePostsRepository extends BaseModelEntityRepository<NotePosts, Long>, QuerydslPredicateExecutor<NotePosts> {

    //вывод постов на основную страницу

    List<NotePosts> findAllByIsDeletedFalseOrderByCreatedTimestampDesc();

    List<NotePosts> findAllByOrderByCreatedTimestampDesc();

    @Query("from NotePosts np where np.scope.code in :codes and np.isDeleted=false order by np.createdTimestamp desc")
    List<NotePosts> customFindAllByScopeCodeInAndIsDeletedFalseOrdered(Iterable<Long> codes);

    @Query("from NotePosts np where np.scope.code between :from and :to and np.isDeleted=false order by np.createdTimestamp desc")
    List<NotePosts> customFindAllByScopeCodeBetweenAndIsDeletedFalseOrdered(Long from, Long to);

}
