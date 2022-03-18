package com.morena.netMain.logic.model.dao;

import com.morena.netMain.logic.utils.Pair;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PSysUsers users= (PSysUsers) o;

        return Objects.equals(uuid, users.uuid);
    }

    @Override
    public int hashCode() {
        return uuid!= null ? uuid.hashCode() : 0;
    }
}
