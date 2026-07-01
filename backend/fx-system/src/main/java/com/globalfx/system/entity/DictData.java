package com.globalfx.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dic_data")
public class DictData extends BaseEntity {

    private String dictType;
    private String dictLabel;
    private String dictValue;
    private Integer sortOrder;
    private String cssClass;
    private String listClass;
    private Integer isDefault;
    private Integer status;
    private Long tenantId;
}
