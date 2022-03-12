package com.morena.netMain.logic.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseModelEntity implements Serializable {
//public abstract class BaseModelEntity implements Serializable, Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id")
    private Long uniqueId;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "created_timestamp")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @Column(name = "modified_timestamp")
    @UpdateTimestamp
    private LocalDateTime modifiedTimestamp;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseModelEntity baseModelEntity = (BaseModelEntity) o;

        return Objects.equals(uuid, baseModelEntity.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

//    @Transient
//    private boolean isNew = true;
//
//    @Override
//    public boolean isNew() {
//        return isNew;
//    }
//
//    @PostLoad
//    void markNotNew() {
//        this.isNew = false;
//    }
//
//    @PrePersist
//    public void initialize() {
//        markNotNew();
//        if (uuid == null) {
//            uuid = UUID.randomUUID();
//        }
//        if (isDeleted==null){
//            isDeleted=false;
//        }
//
//    }
//
//    @Override
//    public Long getId() {
//        return uniqueId;
//    }
}
