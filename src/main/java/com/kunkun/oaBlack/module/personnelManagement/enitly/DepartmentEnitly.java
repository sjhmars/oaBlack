package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_department")
public class DepartmentEnitly {

    @TableId(value = "department_id",type = IdType.AUTO)
    private Integer departmentId;

    @TableField("department_name")
    private String departmentName;

    @TableField("superior_department")
    private Integer superiorDepartment;

    @TableField("is_delete")
    private Integer isDelete;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("user_id")
    private Integer userId;

    @TableField("head_user_name")
    private String headUserName;
}
