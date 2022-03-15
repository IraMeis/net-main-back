package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.pojo.PNotePosts;
import com.morena.netMain.logic.pojo.builder.PNotePostsBuilder;
import com.morena.netMain.logic.repository.DictScopesRepository;
import com.morena.netMain.logic.repository.NotePostsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotePostsService implements CreateOrUpdateEntityMaker<NotePosts,PNotePosts> {

    private final AuthService authService;
    private final NotePostsRepository notePostsRepository;
    private final DictScopesRepository dictScopesRepository;

    @Override
    public NotePosts creatable(PNotePosts pojo){
        NotePosts post = new NotePosts();
        post.setUuid(pojo.getUuid() == null ? UUID.randomUUID() : pojo.getUuid());
        post.setIsDeleted(pojo.getIsDeleted() != null && pojo.getIsDeleted());

        post.setContent(pojo.getContent());
        post.setHeader(pojo.getHeader());
        post.setScope(dictScopesRepository.findOneByCodeAndIsDeletedIsFalse(pojo.getScope().getValue()));
        return post;
    }

    @Override
    public NotePosts updatable(PNotePosts pojo){
        Optional<NotePosts> post = notePostsRepository.findOneByUniqueIdAndIsDeletedFalse(pojo.getUniqueId());
        if(post.isEmpty())
            return null;

        post.get().setScope(dictScopesRepository.findOneByCodeAndIsDeletedIsFalse(
                pojo.getScope().getValue()));
        post.get().setIsDeleted(pojo.getIsDeleted() != null && pojo.getIsDeleted());
        post.get().setContent(pojo.getContent());
        post.get().setHeader(pojo.getHeader());
        return post.get();
    }

    public List<PNotePosts> getAllWithScope(){

        Long to = dictScopesRepository.findTopByOrderByCodeDesc().getCode();
        Long start = authService.getCurrentUserScope();
        List<NotePosts> posts = notePostsRepository.customFindAllByScopeCodeBetweenAndIsDeletedFalseOrdered(start, to);

        return PNotePostsBuilder.toPojoList(posts);
    }

    public List<PNotePosts> getAll(){
        return PNotePostsBuilder.toPojoList(notePostsRepository.findAllByIsDeletedFalseOrderByCreatedTimestampDesc());
    }

    public PNotePosts getById(Long id){
        Optional<NotePosts> post = notePostsRepository.findOneByUniqueIdAndIsDeletedFalse(id);
        if (post.isEmpty())
            return null;

        return PNotePostsBuilder.PostBuild(post.get());
    }

    public void createPost(PNotePosts pNotePosts){
        notePostsRepository.save(creatable(pNotePosts));
    }

    public boolean updatePost(PNotePosts pNotePosts){
        NotePosts post = updatable(pNotePosts);
        if(post == null)
            return false;

        notePostsRepository.save(post);
        return true;
    }

    public void deletePost(Long id){
        notePostsRepository.deleteById(id);
    }

    public List<PNotePosts> doPostFilter(LocalDate from, LocalDate to,
                                        String label, Boolean inHead, Boolean inContent, Boolean inComment,
                                        List<Long> scopes,
                                        List<Long> commentatorIds){

        return PNotePostsBuilder.toPojoList(notePostsRepository.findAllByParsedRequest(from, to,
                label, inHead, inContent, inComment, scopes, commentatorIds));
    }
}
