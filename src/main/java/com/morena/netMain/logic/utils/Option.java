package com.morena.netMain.logic.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Option<Obj, Err> {
    private Obj object;
    private Err or;
}
