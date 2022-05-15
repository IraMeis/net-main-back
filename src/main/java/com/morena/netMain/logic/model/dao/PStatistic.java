package com.morena.netMain.logic.model.dao;

import com.morena.netMain.logic.utils.Pair;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PStatistic {

    private Long userId;
    private String username;
    private UUID userUuid;
    private Boolean isDeleted;
    private Boolean isBanned;
    private Boolean isActive;
    private LocalDateTime registrationTime;
    private LocalDateTime lastVisitTime;
    private Long commentsCount;
    private List<Pair> postsHeadsAndIds;
}
