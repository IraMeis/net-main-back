package com.morena.netMain.logic.model.dao;

import com.morena.netMain.logic.utils.Pair;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PNoteComments {
    private Long id;
    private UUID uuid;
    private String createdTimestamp;
    private String modifiedTimestamp;
    private Boolean isDeleted;

    private Pair author;
    private Long postId;
    private Boolean isModified;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PNoteComments comments = (PNoteComments) o;

        return Objects.equals(uuid, comments.uuid);
    }

    @Override
    public int hashCode() {
        return uuid!= null ? uuid.hashCode() : 0;
    }
}

