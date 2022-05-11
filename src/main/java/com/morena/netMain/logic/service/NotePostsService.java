package com.morena.netMain.logic.service;

import com.morena.netMain.logic.entity.*;
import com.morena.netMain.logic.model.dao.PNotePosts;
import com.morena.netMain.logic.model.builder.PNotePostsBuilder;
import com.morena.netMain.logic.model.filter.PostFilterRequest;
import com.morena.netMain.logic.repository.DictScopesRepository;
import com.morena.netMain.logic.repository.NotePostsRepository;

import com.morena.netMain.logic.repository.ViewPostCommentRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotePostsService implements CreateOrUpdateEntityMaker<NotePosts,PNotePosts> {

    private final SysUsersService sysUsersService;
    private final NotePostsRepository notePostsRepository;
    private final DictScopesRepository dictScopesRepository;
    private final ViewPostCommentRepository viewPostCommentRepository;

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
        Optional<NotePosts> post = notePostsRepository.findOneByUniqueId(pojo.getId());
        if(post.isEmpty())
            return null;

        if(pojo.getScope()!=null && pojo.getScope().getValue()!=null)
        post.get().setScope(dictScopesRepository.findOneByCodeAndIsDeletedIsFalse(
                pojo.getScope().getValue()));

        if(pojo.getIsDeleted()!=null)
        post.get().setIsDeleted(pojo.getIsDeleted());

        if(pojo.getContent()!=null)
        post.get().setContent(pojo.getContent());

        if(pojo.getHeader()!=null)
        post.get().setHeader(pojo.getHeader());

        return post.get();
    }

    public List<PNotePosts> getAllWithScope(){

        Optional<SysUsers> sysUsers = sysUsersService.getCurrentUser();
        if(sysUsers.isEmpty())
            return null;

        Long to = dictScopesRepository.findTopByIsDeletedFalseOrderByCodeDesc().getCode();
        Long start = sysUsers.get().getScope().getCode();

        List<NotePosts> posts = notePostsRepository.customFindAllByScopeCodeBetweenAndIsDeletedFalseOrdered(start, to);

        return PNotePostsBuilder.toPojoList(posts);
    }

    public List<PNotePosts> getAllNotDeleted(){
        return PNotePostsBuilder.toPojoList(notePostsRepository.findAllByIsDeletedFalseOrderByCreatedTimestampDesc());
    }

    public List<PNotePosts> getAll(){
        return PNotePostsBuilder.toPojoList(notePostsRepository.findAllByOrderByCreatedTimestampDesc());
    }

    public PNotePosts getByIdNotDeleted(Long id){
        Optional<NotePosts> post = notePostsRepository.findOneByUniqueIdAndIsDeletedFalse(id);
        if (post.isEmpty())
            return null;

        return PNotePostsBuilder.toPojo(post.get());
    }

    public PNotePosts getById(Long id){
        Optional<NotePosts> post = notePostsRepository.findOneByUniqueId(id);
        if (post.isEmpty())
            return null;

        return PNotePostsBuilder.toPojo(post.get());
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

    public Collection<PNotePosts> getFilteredPosts(PostFilterRequest postFilterRequest){
       List<ViewPostComment> posts = new ArrayList<>();
       viewPostCommentRepository.findAll(predicateParse(postFilterRequest))
               .forEach(posts::add);

       return PNotePostsBuilder.toPojoListFromView(posts)
               .stream()
               .distinct()
               .collect(Collectors.toList());
    }

    private BooleanExpression predicateParse(PostFilterRequest request) {

        List <BooleanExpression> conditions = new ArrayList<>();

        QViewPostComment model = QViewPostComment.viewPostComment;

        if (request.getFrom() != null)
            conditions.add(model.postCreatedTimestamp.after(request.getFrom().atStartOfDay()));

        if (request.getTo() != null)
            conditions.add(model.postCreatedTimestamp.before(request.getTo().atTime(23,59,59)));

        if (request.getLabel() != null) {
            if (!request.getInComment() && !request.getInContent() && !request.getInHead()) {
            } else {
                List<BooleanExpression> labelConditions = new ArrayList<>();

                if (request.getInHead())
                    labelConditions.add(model.postHeader.containsIgnoreCase(request.getLabel()));

                if (request.getInContent())
                    labelConditions.add(model.postContent.containsIgnoreCase(request.getLabel()));

                if (request.getInComment())
                    labelConditions.add(model.commentContent.containsIgnoreCase(request.getLabel()));

                BooleanExpression labelResult = labelConditions.get(0);
                for (int i = 1; i < labelConditions.size(); ++i) {
                    labelResult = labelResult.or(labelConditions.get(i));
                }

                conditions.add(labelResult);
            }
        }

        if(!(request.getCommentatorIds() == null || request.getCommentatorIds().isEmpty()))
            conditions.add(model.commenterUniqueId.in(request.getCommentatorIds()));

        if(!(request.getScopes() == null || request.getScopes().isEmpty()))
            conditions.add(model.postScopeType.in(request.getScopes()));

        BooleanExpression result = conditions.get(0);
        for(int i = 1; i < conditions.size(); ++i)
            result = result.and(conditions.get(i));

        return result;
    }
}
