package com.morena.netMain.logic.entity;

import com.morena.netMain.logic.entity.base.BaseModelDictionaryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dict_scopes", schema = "public")
@Data
@Builder
@AllArgsConstructor
public class DictScopes extends BaseModelDictionaryEntity {
}
