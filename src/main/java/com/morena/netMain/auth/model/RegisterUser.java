package com.morena.netMain.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUser {

    private String username;
    private String password;
    //private String about;
}
