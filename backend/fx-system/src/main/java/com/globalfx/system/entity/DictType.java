package com.globalfx.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dic_type")
public class DictType extends BaseEntity {

    private String dictType;
    private String dictName;
    private Long tenantId;
    private Integer status;
}
