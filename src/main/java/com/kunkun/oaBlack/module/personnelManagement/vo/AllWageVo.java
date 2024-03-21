package com.kunkun.oaBlack.module.personnelManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllWageVo {

    private Integer wagesId;

    private Integer basicSalary;

    private Integer performance;

    private Integer mealSupplement;

    private Integer userId;

    private String userName;

    private Long createTime;

    private String departmentName;

    private String postName;
}
