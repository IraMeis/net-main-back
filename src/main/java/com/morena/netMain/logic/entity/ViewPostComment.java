package com.morena.netMain.logic.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "post_and_comment")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ViewPostComment implements Serializable {

    @Id
    private Long commentUniqueId;

    private UUID commentUuid;

    private Long postUniqueId;

    private UUID postUuid;

    private String postHeader;

    private  String postContent;

    private Long postScopeType;

    private LocalDateTime postCreatedTimestamp;

    private LocalDateTime postModifiedTimestamp;

    private Boolean postIsDeleted;

    private Long commenterUniqueId;

    private String commenterLogin;

    private String commentContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewPostComment viewPostComment= (ViewPostComment) o;

        return Objects.equals(commentUuid, viewPostComment.commentUuid);
    }

    @Override
    public int hashCode() {
        return commentUuid!= null ? commentUuid.hashCode() : 0;
    }
}
