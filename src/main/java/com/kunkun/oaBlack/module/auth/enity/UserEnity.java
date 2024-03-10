package com.kunkun.oaBlack.module.auth.enity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class UserEnity implements Serializable{

    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_password")
    private String userPassword;

    @TableField(value = "create_by")
    private String createBy;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_Id")
    private Integer updateId;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "address")
    private String address;

    @TableField(value = "avatar")
    private String avatar;

    @TableField("email")
    private String email;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "birth")
    private Date birth;

    @TableField(value = "role_ids")
    private String roleIds;

    @TableField(value = "role_name")
    private String roleName;

    @TableField(value = "post_id")
    private Integer postId;

    @TableField(value = "department_id")
    private Integer departmentId;

    @TableField(value = "department_ids")
    private String departmentIds;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    @TableField(value = "last_login_address")
    private String lastLoginAddress;

    @TableField(value = "work_status")
    private Integer workStatus;
}
