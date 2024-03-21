package com.kunkun.oaBlack.module.personnelManagement.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyWageVo {

    private Integer wagesId;

    private Integer basicSalary;

    private Integer userId;

    private String userName;

    private Date createTime;

    private Integer mealSupplement;

    private Integer performance;

    private Integer loseMoney;

    private Integer total;
}
