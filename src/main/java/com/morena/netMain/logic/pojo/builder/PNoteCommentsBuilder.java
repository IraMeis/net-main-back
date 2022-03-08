package com.morena.netMain.logic.pojo.builder;

import com.morena.netMain.logic.entity.NoteComments;
import com.morena.netMain.logic.pojo.PNoteComments;
import com.morena.netMain.logic.utils.Pair;


public class PNoteCommentsBuilder {

    public static PNoteComments AdminCommentBuild(NoteComments noteComments){

        return PNoteComments.builder()

                .uniqueId(noteComments.getUniqueId())
                .uuid(noteComments.getUuid())
                .createdTimestamp(noteComments.getCreatedTimestamp())
                .modifiedTimestamp(noteComments.getModifiedTimestamp())
                .isDeleted(noteComments.getIsDeleted())

                .author(Pair.builder()
                        .value(noteComments.getAuthor().getUniqueId())
                        .label(noteComments.getAuthor().getLogin())
                        .build())
                .postId(noteComments.getPostRef())
                .isModified(noteComments.getIsModified())
                .content(noteComments.getContent())

                .build();
    }

    public static PNoteComments UserCommentBuild(NoteComments noteComments){

        return PNoteComments.builder()

                .uniqueId(noteComments.getUniqueId())
                .createdTimestamp(noteComments.getCreatedTimestamp())

                .author(Pair.builder()
                        .value(noteComments.getAuthor().getUniqueId())
                        .label(noteComments.getAuthor().getLogin())
                        .build())
                .postId(noteComments.getPostRef())
                .isModified(noteComments.getIsModified())
                .content(noteComments.getContent())

                .build();
    }

    //public static List<PNoteComments> toPojoList
}
