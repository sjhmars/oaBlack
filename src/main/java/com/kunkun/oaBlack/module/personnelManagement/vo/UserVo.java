package com.kunkun.oaBlack.module.personnelManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private Integer userId;

    private String userName;

    private String email;

    private String mobile;

    private Date birth;

    private Integer workStatus;

    private Integer departmentId;

    private Integer postId;

    private Integer sex;

    private String departmentName;

    private String postName;

    private String roleName;
}
