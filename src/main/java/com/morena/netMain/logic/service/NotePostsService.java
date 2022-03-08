package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.pojo.PNotePosts;
import com.morena.netMain.logic.pojo.builder.PNotePostsBuilder;
import com.morena.netMain.logic.repository.DictScopesRepository;
import com.morena.netMain.logic.repository.NotePostsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class NotePostsService implements RoleChecker{

    private final SysUsersService sysUsersService;
    private final AuthService authService;
    private final NotePostsRepository notePostsRepository;
    private final DictScopesRepository dictScopesRepository;

    @Override
    public boolean isAdmin() {
        return authService.getCurrentUserRoles().stream().anyMatch(List.of(
                system,
                post_modifier)
                ::contains);
    }

    public List<PNotePosts> getAllWithScope(){
        Optional<SysUsers> sysUsers = sysUsersService.getCurrentUser();
        if(sysUsers.isEmpty())
            return null;

        Long to = dictScopesRepository.findTopByOrderByCodeDesc().getCode();
        Long start = sysUsers.get().getScope().getCode();
        List<NotePosts> posts = notePostsRepository.customFindAllByScopeCodeBetweenAndIsDeletedFalseOrdered(start, to);

        return PNotePostsBuilder.toPojoList(posts);
    }

    public List<PNotePosts> getAll(){
        return PNotePostsBuilder.toPojoList(notePostsRepository.findAllByIsDeletedFalseOrderByCreatedTimestamp());
    }
}
