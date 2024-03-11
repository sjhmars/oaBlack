package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDao {

    @NotEmpty
    private String userName;

    private Integer sex;

    private Date birth;

    private String mobile;

    private Integer workStatus;

    private Integer departmentId;

    private Integer postId;

    @NotEmpty
    private String userPassword;

    private Integer roleId;
}
