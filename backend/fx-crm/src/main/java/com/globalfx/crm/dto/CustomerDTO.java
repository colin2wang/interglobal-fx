package com.globalfx.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "客户姓名不能为空")
    private String fullName;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    private String firstName;
    private String lastName;
    private String phone;
    private String country;
    private String city;
    private String address;
    private LocalDate birthDate;
    private Integer customerType;
    private Integer source;
    private Long ibId;
    private Long managerId;
    private Integer status;
    private String remark;
}
