package com.morena.netMain.logic.entity;

import com.morena.netMain.logic.entity.base.BaseModelEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "note_comments", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NoteComments extends BaseModelEntity {

    @Column(name = "content")
    private String content;

    @Column(name = "is_modified")
    private Boolean isModified;

    @Column(name = "post_ref")
    private Long postRef;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "author_ref", referencedColumnName = "unique_id")
    private SysUsers author;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "link_comment_reply_to", joinColumns = @JoinColumn(name = "comment_ref"), inverseJoinColumns = @JoinColumn(name = "reply_to_ref"))
//    @Fetch(FetchMode.JOIN)
//    private Set<SysUsers> toUsers;
}
