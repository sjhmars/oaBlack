package com.kunkun.oaBlack.module.personnelManagement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTreeVo {

    private Integer departmentId;

    private String departmentName;

    private Integer superiorDepartment;

    private Integer userId;

    private String userName;

    private List<DepartmentTreeVo> children = new ArrayList<>();
}
