package com.morena.netMain.logic.pojo;

import com.morena.netMain.logic.utils.Pair;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PNoteComments {
    private Long uniqueId;
    private UUID uuid;
    private LocalDateTime createdTimestamp;
    private LocalDateTime modifiedTimestamp;
    private Boolean isDeleted;

    private Pair author;
    private Long postId;
    private Boolean isModified;
    private String content;
}

