package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDao {

    private String userName;

    private Integer sex;

    private Date birth;

    private String mobile;

    private Integer workStatus;

    private Integer departmentId;

    private Integer postId;

    private String userPassword;

    private String role_ids;
}
