package com.morena.netMain.logic.entity;

import com.morena.netMain.logic.entity.base.BaseModelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "note_posts", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotePosts extends BaseModelEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "header")
    private String header;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "scope_type", referencedColumnName = "code")
    private DictScopes scope;

}
