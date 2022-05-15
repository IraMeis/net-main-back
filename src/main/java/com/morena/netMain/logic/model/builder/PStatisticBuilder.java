package com.morena.netMain.logic.model.builder;

import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.model.dao.PStatistic;
import com.morena.netMain.logic.utils.LocalDateTimeConvertor;
import com.morena.netMain.logic.utils.Pair;

import java.util.List;

public class PStatisticBuilder {

    private static LocalDateTimeConvertor convertor = new LocalDateTimeConvertor();

    public static PStatistic toPojo(SysUsers user, Long commentsCount, List<Pair> posts){
        return PStatistic.builder()
                .userId(user.getUniqueId())
                .username(user.getLogin())
                .userUuid(user.getUuid())
                .isBanned(user.getToken() != null && user.getToken().getIsDeleted())
                .isActive(user.getIsActive())
                .isDeleted(user.getIsDeleted())
                .lastVisitTime(user.getToken() == null ? null :
                        convertor.convert(user.getToken().getModifiedTimestamp()))
                .registrationTime(convertor.convert(user.getCreatedTimestamp()))

                .commentsCount(commentsCount)
                .postsHeadsAndIds(posts)

                .build();
    }
}
