package com.kunkun.oaBlack.module.personnelManagement.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.kunkun.oaBlack.common.util.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckDao {

    private Integer pageNumber;

    private Integer pageSize;

    private Long startTime;

    private Long endTime;
}
