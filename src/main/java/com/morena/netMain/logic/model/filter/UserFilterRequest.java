package com.morena.netMain.logic.model.filter;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserFilterRequest implements NullFilterChecker{
    private LocalDate from;
    private LocalDate to;
    private String label;
    private List<Long> scopes;
    private Boolean isDeleted;
    private Boolean isActive;
    private Boolean isUser;

    @Override
    public boolean isNullFilter(){
        return from == null && to == null && label == null &&
                (scopes == null || scopes.isEmpty()) &&
                isDeleted == null && isActive == null && isUser == null;
    }
}
