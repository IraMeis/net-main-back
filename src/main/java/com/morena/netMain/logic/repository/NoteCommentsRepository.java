package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.NoteComments;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteCommentsRepository extends BaseModelEntityRepository<NoteComments, Long> {

    List<NoteComments> findAllByPostRefAndIsDeletedFalseOrderByCreatedTimestamp(Long postRef);

    List<NoteComments> findAllByPostRefOrderByCreatedTimestamp(Long postRef);

    Long countByAuthor(SysUsers sysUsers);

    @Override
    @Modifying
    @Query("update NoteComments nc set nc.isDeleted=true where nc.uniqueId=:id")
    void deleteById(Long id);
}
