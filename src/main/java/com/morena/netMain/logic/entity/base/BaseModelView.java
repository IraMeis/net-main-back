package com.morena.netMain.logic.entity.base;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode
public abstract class BaseModelView implements Serializable {

    @Id
    private Long uniqueId;
    private UUID uuid;
}