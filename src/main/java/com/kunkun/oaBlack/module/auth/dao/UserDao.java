package com.kunkun.oaBlack.module.auth.dao;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserDao {
    private String userName;

    @NotEmpty
    @Pattern(regexp = "^[1][0-9]{10}$",message = "手机号格式不正确")
    private String userMobile;

    @NotEmpty
    private String userPassword;

    @NotEmpty
    private String loginAddress;
}
