package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kunkun.oaBlack.common.config.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("t_leave")
public class LeaveEntity {

    @TableId(value = "leave_id",type = IdType.AUTO)
    private Integer leaveId;

    @TableField("begin_time")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date beginTime;

    @TableField("end_time")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date endTime;

    @TableField("day")
    private float day;

    @TableField("reviewer_user_id")
    private Integer reviewerUserId;

    @TableField("reviewer_user_name")
    private String reviewerUserName;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user_id")
    private Integer createUserId;

    @TableField("create_user_name")
    private String createUserName;

    @TableField("leave_type")
    private Integer leaveType;

    @TableField("leave_details")
    private String leaveDetails;

}
