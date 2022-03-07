package com.morena.netMain.auth.model;

import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.entity.base.BaseModelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sys_tokens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysTokens extends BaseModelEntity {

    @Column(name = "content")
    private byte[] content;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_ref", referencedColumnName = "unique_id")
    private SysUsers owner;

//    @Column(name = "user_ref")
//    private Long userRef;

    public SysTokens(UUID uuid, boolean isDeleted, byte[] content, SysUsers owner){
        this.setUuid(uuid);
        this.setIsDeleted(isDeleted);
        this.content=content;
        this.owner=owner;
    }

}
