package com.kunkun.oaBlack.module.personnelManagement.enitly;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_check")
public class CheckEntity {

    @TableId(type = IdType.AUTO,value = "check_id")
    private Integer checkId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "this_date")
    private LocalDateTime thisDate;

    @TableField(value = "check_start_time")
    private LocalDateTime checkStartTime;

    @TableField(value = "check_end_time")
    private LocalDateTime checkEndTime;

    @TableField(value = "late_time")
    private String lateTime;

    @TableField(value = "is_late")
    private Integer isLate;

    @TableField(value = "is_early")
    private Integer isEarly;

    @TableField("early_time")
    private String earlyTime;

    @TableField("leave_status")
    private Integer leaveStatus;

    @TableField("address")
    private String address;
}
