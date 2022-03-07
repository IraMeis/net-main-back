package com.morena.netMain.logic.pojo;

import com.morena.netMain.logic.utils.Pair;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PSysUsers {

    private Long uniqueId;
    private UUID uuid;
    private LocalDateTime createdTimestamp;
    private LocalDateTime modifiedTimestamp;
    private Boolean isDeleted;

    private String about;
    private String login;
    //private String password;
    private Boolean isActive;
    private Boolean isUser;
    private Collection<Pair> roles;
    private Pair scope;

    private Boolean isTokenAllowed;

}
