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
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_post")
public class PostEnity {

    @TableId(value = "post_id",type = IdType.AUTO)
    private Integer postId;

    @TableField("post_name")
    private String postName;

    @TableField("post_status")
    private Integer postStatus;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by_user")
    private Integer createByUser;
}
