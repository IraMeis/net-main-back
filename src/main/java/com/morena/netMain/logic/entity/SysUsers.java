package com.morena.netMain.logic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.morena.netMain.auth.model.SysTokens;
import com.morena.netMain.logic.entity.base.BaseModelEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sys_users", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUsers extends BaseModelEntity {

    @Column(name = "login")
    private String login;

    @Column (name = "about")
    private String about;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "is_active")
    private Boolean isActive;

    @JsonIgnore
    @Column(name = "is_user")
    private Boolean isUser;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "link_users_roles",
            joinColumns = @JoinColumn(name = "user_ref", referencedColumnName = "unique_id"),
            inverseJoinColumns = @JoinColumn(name = "role_ref", referencedColumnName = "unique_id")
   )
    private Collection<DictRoles> rawRoles = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "scope_type", referencedColumnName = "code")
    private DictScopes scope;

    @JsonIgnore
    @OneToOne(mappedBy ="owner", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private SysTokens token;
}
