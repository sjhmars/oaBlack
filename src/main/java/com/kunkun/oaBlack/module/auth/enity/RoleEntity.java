package com.kunkun.oaBlack.module.auth.enity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
public class RoleEntity {

    @TableId(value = "role_id",type = IdType.AUTO)
    private Integer roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("create_time")
    private Date createTime;

    @TableField("role_authority")
    private String roleAuthority;

    @TableField("update_time")
    private Date updateTime;

    @TableField("status")
    private Integer status;
}
