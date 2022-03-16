package com.morena.netMain.logic.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum Scope {
    scope_private(1L),
    scope_trusted(2L),
    scope_viewers(3L),
    scope_public(4L);

    @Getter
    private final Long code;

    @Getter
    transient public static Map<Long,String> codes = Map.of(
             1L,"scope_private",
            2L, "scope_trusted",
            3L, "scope_viewers",
            4L, "scope_public");
}
