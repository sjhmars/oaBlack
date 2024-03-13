package com.kunkun.oaBlack.module.personnelManagement.vo;

import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTreeUserVo {

    private Integer departmentId;

    private String departmentName;

    private Integer superiorDepartment;

    private Integer userId;

    private String headUserName;

    private List<DUserVo> dUserVoList;

    private List<DepartmentTreeUserVo> children = new ArrayList<>();
}
