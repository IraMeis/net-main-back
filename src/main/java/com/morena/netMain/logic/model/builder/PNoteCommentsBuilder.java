package com.morena.netMain.logic.model.builder;

import com.morena.netMain.logic.entity.NoteComments;
import com.morena.netMain.logic.model.dao.PNoteComments;
import com.morena.netMain.logic.utils.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class PNoteCommentsBuilder {

    public static PNoteComments toAdminPojo(NoteComments noteComments){

        return PNoteComments.builder()

                .id(noteComments.getUniqueId())
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

    public static PNoteComments toUserPojo(NoteComments noteComments){

        return PNoteComments.builder()

                .id(noteComments.getUniqueId())
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

    public static List<PNoteComments> toUserPojoList(List<NoteComments> nc){
        if (nc == null)
            return Collections.emptyList();
        return nc.stream()
                .map(PNoteCommentsBuilder::toUserPojo)
                .collect(Collectors.toList());
    }

    public static List<PNoteComments> toAdminPojoList(List<NoteComments> nc){
        if (nc == null)
            return Collections.emptyList();
        return nc.stream()
                .map(PNoteCommentsBuilder::toAdminPojo)
                .collect(Collectors.toList());
    }
}
