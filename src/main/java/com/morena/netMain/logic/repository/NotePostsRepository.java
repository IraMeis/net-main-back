package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.DictScopes;
import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotePostsRepository extends BaseModelEntityRepository<NotePosts, Long> {

    //вывод постов на основную страницу

    List<NotePosts> findAllByIsDeletedFalseOrderByCreatedTimestamp();

    List<NotePosts> findAllByIsDeletedFalseAndScopeOrderByCreatedTimestamp(DictScopes scope);

//    @Query("select np from NotePosts np where np.scope.code in :scopes and np.isDeleted=false order by np.createdTimestamp")
//    List<NotePosts> customFindAllByScopeCodeInAndIsDeletedFalseOrdered(Set<Long> scopes);
//
    @Query("from NotePosts np where np.scope.code between :from and :to and np.isDeleted=false order by np.createdTimestamp")
    List<NotePosts> customFindAllByScopeCodeBetweenAndIsDeletedFalseOrdered(Long from, Long to);

   // List<NotePosts> findAllByIsDeletedFalseAndScopeInOrderByCreatedTimestamp(Collection<Long> scopes);
    List<NotePosts> findAllByIsDeletedFalseAndScopeBetweenOrderByCreatedTimestamp(DictScopes from, DictScopes to);

    //фильтр

    List<NotePosts> findAllByIsDeletedAndScopeInAndCreatedTimestampBetweenAndContentContainingAndHeaderContainingOrderByCreatedTimestamp(
            Boolean isDeleted,
            Iterable<DictScopes> scope,
            LocalDateTime fromTime, LocalDateTime toTime,
            String contentLabel,
            String headLabel);
}
