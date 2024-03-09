package com.kunkun.oaBlack.module.personnelManagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddDepartmentDao {


    private Integer userId;

    private Integer superiorDepartment;

    @NotEmpty
    private String departmentName;
}
