package com.kunkun.oaBlack.module.personnelManagement.controller;

import cn.hutool.core.util.ObjectUtil;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.WagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.WagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wages")
@Api("工资模块")
public class WagesController {

    @Autowired
    private WagesService wagesService;

    @ApiOperation("查询所有工资条")
    @PostMapping("/getWage")
    public ResultUtil getWage(){
        return ResultUtil.success(wagesService.list());
    }

    @ApiOperation("根据ID查询工资条")
    @PostMapping("/getWage")
    public ResultUtil getWageById(WagesDao wagesDao){
        return ResultUtil.success(wagesService.getById(wagesDao.getWagesId()));
    }

    @ApiOperation("开工资")
    @PostMapping("/addWage")
    public ResultUtil addWage(WagesDao wagesDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        WagesEntity wagesEntity = wagesService.addWages(wagesDao,authentication);
        if (ObjectUtil.isNotNull(wagesEntity)){
            return ResultUtil.success(wagesEntity);
        }
        return ResultUtil.faile("开工资失败");
    }
}
