package com.globalfx.system.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserVO {

    private Long id;
    private String userNo;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatarUrl;
    private String post;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private List<RoleVO> roles;
    private LocalDateTime createdTime;
    private String remark;
}
