package com.morena.netMain.logic.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message implements ErrorDescription{
    private String message;
}
