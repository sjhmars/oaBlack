package com.kunkun.oaBlack.module.personnelManagement.dao;

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
public class TempWagesDao {

    private Integer tempWagesId;

    private Integer basicSalary;

    private Integer userId;

    private Integer mealSupplement;

    private Integer performance;

    private Integer isDelete;
}
