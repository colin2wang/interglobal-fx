package com.globalfx.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 50, message = "用户名长度2-50")
    private String username;

    @Size(min = 6, max = 200, message = "密码长度6-200")
    private String password;

    @Size(max = 100, message = "昵称最长100")
    private String nickname;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(max = 30, message = "手机号最长30")
    private String phone;

    private Long deptId;
    private String post;
    private Integer status;
    private List<Long> roleIds;
    private String remark;
}
