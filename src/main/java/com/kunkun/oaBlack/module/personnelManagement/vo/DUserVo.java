package com.kunkun.oaBlack.module.personnelManagement.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DUserVo {
    private Integer userId;

    private String userName;

    private Date createTime;

    private String address;

    private String email;

    private String mobile;

    private Integer sex;

    private Integer status;

    private Date birth;

    private String roleName;

    private String nickname;

    private Integer workStatus;

    private String postName;
}
