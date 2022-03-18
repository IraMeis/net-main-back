package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.NoteComments;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.model.dao.PNoteComments;
import com.morena.netMain.logic.model.builder.PNoteCommentsBuilder;
import com.morena.netMain.logic.repository.NoteCommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class NoteCommentsService implements RoleChecker, CreateOrUpdateEntityMaker<NoteComments, PNoteComments>{

    private final NoteCommentsRepository noteCommentsRepository;
    private final AuthService authService;
    private final SysUsersService sysUsersService;

    @Override
    public boolean isAdmin(){
       return authService.getCurrentUserRoles().stream().anyMatch(List.of(
                system,
                comment_admin_viewer,
                comment_admin_deleter)
                ::contains);
    }

    @Override
    public NoteComments creatable(PNoteComments pojo) {
        NoteComments comment = new NoteComments();
        comment.setUuid(pojo.getUuid() == null ? UUID.randomUUID() : pojo.getUuid());
        comment.setIsDeleted(pojo.getIsDeleted() != null && pojo.getIsDeleted());

        Optional<SysUsers> user = sysUsersService.getUserByIdNotDeleted(pojo.getAuthor().getValue());
        if(user.isEmpty())
            return null;

        comment.setAuthor(user.get());
        comment.setContent(pojo.getContent());
        comment.setIsModified(false);
        comment.setPostRef(pojo.getPostId());
        return comment;
    }

    @Override
    public NoteComments updatable(PNoteComments pojo) {
        Optional<NoteComments> comment = noteCommentsRepository.findOneByUniqueIdAndIsDeletedFalse(pojo.getUniqueId());
        if(comment.isEmpty())
            return null;

        comment.get().setContent(pojo.getContent());
        comment.get().setIsModified(true);
        return comment.get();
    }

    public PNoteComments getCommentById(Long id){
        boolean admin = isAdmin();
        Optional<NoteComments> comment;

        if(admin)
            comment = noteCommentsRepository.findOneByUniqueId(id);
        else
            comment = noteCommentsRepository.findOneByUniqueIdAndIsDeletedFalse(id);

        if (comment.isEmpty())
            return null;

        if(admin)
            return PNoteCommentsBuilder.toAdminPojo(comment.get());
        return PNoteCommentsBuilder.toUserPojo(comment.get());
    }

    public List<PNoteComments> getAllByPostRef (Long postId){
        boolean admin = isAdmin();
        List<NoteComments> comments;

        if(admin)
            comments = noteCommentsRepository.findAllByPostRefOrderByCreatedTimestamp(postId);
        else
            comments = noteCommentsRepository.findAllByPostRefAndIsDeletedFalseOrderByCreatedTimestamp(postId);

        if (admin)
            return PNoteCommentsBuilder.toAdminPojoList(comments);
        return PNoteCommentsBuilder.toUserPojoList(comments);
    }

    public boolean createComment(PNoteComments pNoteComments){
        NoteComments comment = creatable(pNoteComments);
        if(comment == null)
            return false;

        noteCommentsRepository.save(comment);
        return true;
    }

    public boolean updateComment(PNoteComments pNoteComments){
        NoteComments comment = updatable(pNoteComments);
        if(comment == null)
            return false;

        noteCommentsRepository.save(comment);
        return true;
    }

    public void deleteComment(Long id){
        noteCommentsRepository.deleteById(id);
    }
}
