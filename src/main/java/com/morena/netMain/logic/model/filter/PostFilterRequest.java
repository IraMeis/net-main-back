package com.morena.netMain.logic.model.filter;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PostFilterRequest implements NullFilterChecker{

    private LocalDate from;
    private LocalDate to;
    private String label;
    private Boolean inHead;
    private Boolean inContent;
    private Boolean inComment;
    private List<Long> scopes;
    private List<Long> commentatorIds;

    @Override
    public boolean isNullFilter() {
        return from == null && to == null && label == null &&
                (scopes == null || scopes.isEmpty()) &&
                (commentatorIds == null || commentatorIds.isEmpty());
    }
}
