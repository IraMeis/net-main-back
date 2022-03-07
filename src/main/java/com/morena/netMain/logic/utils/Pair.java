package com.morena.netMain.logic.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pair {
    private Long value;
    private String label;
}