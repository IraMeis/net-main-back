package com.morena.netMain.logic.model.builder;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.entity.ViewPostComment;
import com.morena.netMain.logic.model.dao.PNotePosts;
import com.morena.netMain.logic.model.enums.Scope;
import com.morena.netMain.logic.utils.LocalDateTimeConvertor;
import com.morena.netMain.logic.utils.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PNotePostsBuilder {

    private static LocalDateTimeConvertor convertor = new LocalDateTimeConvertor();

    public static PNotePosts toPojo(NotePosts notePosts){

        return PNotePosts.builder()

                .id(notePosts.getUniqueId())
                .uuid(notePosts.getUuid())
                .createdTimestamp(convertor.convert(notePosts.getCreatedTimestamp()))
                .modifiedTimestamp(convertor.convert(notePosts.getModifiedTimestamp()))
                .isDeleted(notePosts.getIsDeleted())

                .header(notePosts.getHeader())
                .content(notePosts.getContent())
                .scope(Pair.builder()
                        .value(notePosts.getScope().getCode())
                        .label(notePosts.getScope().getName())
                        .build())

                .build();
    }

    public static PNotePosts toPojoFromView(ViewPostComment viewPostComment){

        return PNotePosts.builder()

                .id(viewPostComment.getPostUniqueId())
                .uuid(viewPostComment.getPostUuid())
                .createdTimestamp(convertor.convert(viewPostComment.getPostCreatedTimestamp()))
                .modifiedTimestamp(convertor.convert(viewPostComment.getPostModifiedTimestamp()))
                .isDeleted(viewPostComment.getPostIsDeleted())

                .header(viewPostComment.getPostHeader())
                .content(viewPostComment.getPostContent())
                .scope(Pair.builder()
                        .value(viewPostComment.getPostScopeType())
                        .label(Scope.codes.get(viewPostComment.getPostScopeType()))
                        .build())

                .build();
    }

    public static List<PNotePosts> toPojoList (List<NotePosts> np){
        if (np == null)
            return Collections.emptyList();
        return np.stream()
                .map(PNotePostsBuilder::toPojo)
                .collect(Collectors.toList());
    }

    public static List<PNotePosts> toPojoListFromView (List<ViewPostComment> viewPostComments){
        if (viewPostComments == null)
            return Collections.emptyList();
        return viewPostComments.stream()
                .map(PNotePostsBuilder::toPojoFromView)
                .collect(Collectors.toList());
    }
}
