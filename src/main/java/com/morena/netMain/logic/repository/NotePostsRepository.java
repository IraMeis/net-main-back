package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotePostsRepository extends BaseModelEntityRepository<NotePosts, Long> {

    List<NotePosts> findAllByIsDeletedFalseOrderByCreatedTimestampDesc();

    List<NotePosts> findAllByOrderByCreatedTimestampDesc();

    @Query("from NotePosts np where np.scope.code in :codes and np.isDeleted=false order by np.createdTimestamp desc")
    List<NotePosts> customFindAllByScopeCodeInAndIsDeletedFalseOrdered(Iterable<Long> codes);

    @Query("from NotePosts np where np.scope.code between :from and :to and np.isDeleted=false order by np.createdTimestamp desc")
    List<NotePosts> customFindAllByScopeCodeBetweenAndIsDeletedFalseOrdered(Long from, Long to);

    @Query("from NotePosts pp where pp.uniqueId in (select distinct np.uniqueId " +
            "from NotePosts np inner join NoteComments nc on np.uniqueId = nc.postRef " +
            "where nc.author.uniqueId=:commenterId) " +
            "order by pp.header")
    List<NotePosts> customFindAllByCommenter(Long commenterId);

    @Override
    @Modifying
    @Query("update NotePosts nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);

}
