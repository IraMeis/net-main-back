package com.morena.netMain.logic.pojo.builder;

import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.pojo.PNotePosts;
import com.morena.netMain.logic.utils.Pair;


import java.util.List;
import java.util.stream.Collectors;

public class PNotePostsBuilder {
    public static PNotePosts PostBuild(NotePosts notePosts){

        return PNotePosts.builder()

                .uniqueId(notePosts.getUniqueId())
                .uuid(notePosts.getUuid())
                .createdTimestamp(notePosts.getCreatedTimestamp())
                .modifiedTimestamp(notePosts.getModifiedTimestamp())
                .isDeleted(notePosts.getIsDeleted())

                .header(notePosts.getHeader())
                .content(notePosts.getContent())
                .scope(Pair.builder()
                        .value(notePosts.getScope().getCode())
                        .label(notePosts.getScope().getName())
                        .build())

                .build();
    }

    public static List<PNotePosts> toPojoList (List<NotePosts> np){
        return np.stream()
                .map(PNotePostsBuilder::PostBuild)
                .collect(Collectors.toList());
    }
}
