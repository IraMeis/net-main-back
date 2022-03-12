package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.pojo.PNotePosts;
import com.morena.netMain.logic.pojo.builder.PNotePostsBuilder;
import com.morena.netMain.logic.repository.DictScopesRepository;
import com.morena.netMain.logic.repository.NotePostsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class NotePostsService implements RoleChecker, CreateOrUpdate<NotePosts,PNotePosts>{

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

    @Override
    public NotePosts create(PNotePosts pNotePosts){
        NotePosts post = new NotePosts();
        post.setUuid(pNotePosts.getUuid() == null ? UUID.randomUUID() : pNotePosts.getUuid());
        post.setIsDeleted(pNotePosts.getIsDeleted() != null && pNotePosts.getIsDeleted());
        post.setContent(pNotePosts.getContent());
        post.setHeader(pNotePosts.getHeader());
        post.setScope(dictScopesRepository.findByCodeAndIsDeletedIsFalse(pNotePosts.getScope().getValue()));
        return post;
    }

    @Override
    public NotePosts update(PNotePosts pNotePosts){
        Optional<NotePosts> post = notePostsRepository.findByUniqueIdAndIsDeletedFalse(pNotePosts.getUniqueId());
        if(post.isEmpty())
            return null;

        post.get().setScope(dictScopesRepository.findByCodeAndIsDeletedIsFalse(
                pNotePosts.getScope().getValue()));
        post.get().setIsDeleted(pNotePosts.getIsDeleted() != null && pNotePosts.getIsDeleted());
        post.get().setContent(pNotePosts.getContent());
        post.get().setHeader(pNotePosts.getHeader());
        return post.get();
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

    public PNotePosts getById(Long id){
        Optional<NotePosts> post = notePostsRepository.findByUniqueIdAndIsDeletedFalse(id);
        if (post.isEmpty())
            return null;

        return PNotePostsBuilder.PostBuild(post.get());
    }

    public void createPost(PNotePosts pNotePosts){
        notePostsRepository.save(create(pNotePosts));
    }

    public boolean updatePost(PNotePosts pNotePosts){
        NotePosts post = update(pNotePosts);
        if(post == null)
            return false;

        notePostsRepository.save(post);
        return true;
    }

    public void deletePost(Long id){
        notePostsRepository.deleteById(id);
    }
}
