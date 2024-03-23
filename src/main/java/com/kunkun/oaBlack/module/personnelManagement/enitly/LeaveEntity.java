package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("t_leave")
public class LeaveEntity {

    @TableId("leave_id")
    private Integer leaveId;

    @TableField("begin_time")
    private Date beginTime;

    @TableField("end_time")
    private Date endTime;

    @TableField("day")
    private Integer day;

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
