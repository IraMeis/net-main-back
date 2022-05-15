package com.morena.netMain.logic.model.builder;

import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.model.dao.PStatistic;
import com.morena.netMain.logic.utils.Pair;

import java.util.List;

public class PStatisticBuilder {

    public static PStatistic toPojo(SysUsers user, Long commentsCount, List<Pair> posts){
        return PStatistic.builder()
                .userId(user.getUniqueId())
                .username(user.getLogin())
                .userUuid(user.getUuid())
                .isBanned(user.getToken() != null && user.getToken().getIsDeleted())
                .isActive(user.getIsActive())
                .isDeleted(user.getIsDeleted())
                .lastVisitTime(user.getToken() == null ? null : user.getToken().getModifiedTimestamp())
                .registrationTime(user.getCreatedTimestamp())

                .commentsCount(commentsCount)
                .postsHeadsAndIds(posts)

                .build();
    }
}
