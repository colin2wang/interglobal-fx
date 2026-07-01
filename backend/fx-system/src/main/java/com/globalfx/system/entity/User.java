package com.globalfx.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.globalfx.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("usr_user")
public class User extends BaseEntity {

    private String userNo;
    private Long tenantId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String avatarUrl;
    private Long deptId;
    private String post;
    private Integer status;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;
    private Integer loginFailCount;
    private LocalDateTime lockExpireTime;
    private LocalDateTime passwordExpireTime;
}
