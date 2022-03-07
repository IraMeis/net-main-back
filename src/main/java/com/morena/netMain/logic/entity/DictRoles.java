package com.morena.netMain.logic.entity;

import com.morena.netMain.logic.entity.base.BaseModelDictionaryEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dict_roles", schema = "public")
@Data
@NoArgsConstructor
public class DictRoles extends BaseModelDictionaryEntity {
}
