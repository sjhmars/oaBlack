package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDao {

    private Integer userId;

    private String userName;

    private Integer sex;

    private Date birth;

    @Pattern(regexp = "^[1][0-9]{10}$",message = "手机号格式不正确")
    private String mobile;

    private Integer workStatus;

    private Integer departmentId;

    private Integer postId;

    private String userPassword;

    private Integer roleId;

    private String nikeName;

    private String email;
}
