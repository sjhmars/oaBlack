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
@TableName("t_temp_wage")
public class TempWagesEntity {
    @TableId(value = "temp_wages_id",type = IdType.AUTO)
    private Integer tempWagesId;

    @TableField(value = "basic_salary")
    private Integer basicSalary;

    @TableField("user_id")
    private Integer userId;

    @TableField("user_name")
    private String userName;

    @TableField(value = "update_by_userId")
    private Integer updateByUserId;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_user_id")
    private Integer createUserId;

    @TableField(value = "meal_supplement")
    private Integer mealSupplement;

    @TableField(value = "performance")
    private Integer performance;

    @TableField(value = "is_delete")
    private Integer isDelete;
}
