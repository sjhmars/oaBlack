package com.kunkun.oaBlack.module.personnelManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndDepartmentVo {

    private Integer userId;

    private String userName;

    private String email;

    private String mobile;

    private Long birth;

    private Integer work_status;

    private Integer departmentId;

    private Integer postId;

    private String  departmentAndPost;

    private Integer sex;

    private String departmentName;

    private String postName;

    private Integer roleId;
}
