package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.NoteComments;
import com.morena.netMain.logic.pojo.PNoteComments;
import com.morena.netMain.logic.pojo.builder.PNoteCommentsBuilder;
import com.morena.netMain.logic.repository.NoteCommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class NoteCommentsService implements RoleChecker{

    private final NoteCommentsRepository noteCommentsRepository;
    private final AuthService authService;

    @Override
    public boolean isAdmin(){
       return authService.getCurrentUserRoles().stream().anyMatch(List.of(
                system,
                comment_admin_viewer,
                comment_admin_deleter)
                ::contains);
    }

    public PNoteComments getCommentById(Long id){
        Optional<NoteComments> comment = noteCommentsRepository.findByUniqueIdAndIsDeletedFalse(id);
        if (comment.isEmpty())
            return null;

        if(isAdmin())
            return PNoteCommentsBuilder.AdminCommentBuild(comment.get());
        return PNoteCommentsBuilder.UserCommentBuild(comment.get());
    }
}
