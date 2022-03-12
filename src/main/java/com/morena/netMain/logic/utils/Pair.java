package com.morena.netMain.logic.utils;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Pair {
    private Long value;
    private String label;
}