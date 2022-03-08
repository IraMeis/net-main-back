package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.NoteComments;
import com.morena.netMain.logic.repository.base.BaseModelEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteCommentsRepository extends BaseModelEntityRepository<NoteComments, Long> {

    List<NoteComments> findAllByPostRefAndIsDeletedFalseOrderByCreatedTimestamp(Long postRef);

    List<NoteComments> findAllByPostRefOrderByCreatedTimestamp(Long postRef);
}
