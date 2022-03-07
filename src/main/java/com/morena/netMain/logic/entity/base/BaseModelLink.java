package com.morena.netMain.logic.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
@EqualsAndHashCode
public abstract class BaseModelLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id")
    private Long uniqueId;

}
