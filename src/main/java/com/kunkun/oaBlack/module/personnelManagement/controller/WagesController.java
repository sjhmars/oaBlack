package com.kunkun.oaBlack.module.personnelManagement.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.PagesDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WageNameDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.WagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.WagesService;
import com.kunkun.oaBlack.module.personnelManagement.vo.AllWageVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.MyWageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wages")
@Api(tags = "工资模块")
public class WagesController {

    @Autowired
    private WagesService wagesService;

    @ApiOperation("查询所有工资条")
    @PostMapping("/getWage")
    public ResultUtil getWage(@RequestBody PagesDao pagesDao){
        IPage<AllWageVo> wagesEntityPage = wagesService.selectAllWages(pagesDao);
        return ResultUtil.success(wagesEntityPage);
    }

    @ApiOperation("根据ID查询工资条")
    @PostMapping("/getWageById")
    public ResultUtil getWageById(@RequestBody WagesDao wagesDao){
        return ResultUtil.success(wagesService.getById(wagesDao.getWagesId()));
    }

    @ApiOperation("查询自己的工资")
    @PostMapping("/getMyWage")
    public ResultUtil getMyWage(@RequestBody PagesDao pagesDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        IPage<MyWageVo> myWage = wagesService.getMyWages(authentication,pagesDao);
        return ResultUtil.success(myWage);
    }

    @ApiOperation("根据名字查询工资")
    @PostMapping("/getWageByName")
    public ResultUtil getWageByName(@RequestBody WageNameDao wageNameDao){
        IPage<WagesEntity> wagesEntityIPage = wagesService.selectByName(wageNameDao);
        return ResultUtil.success(wagesEntityIPage);
    }

    @ApiOperation("开工资")
    @PostMapping("/addWage")
    public ResultUtil addWage(@RequestBody WagesDao wagesDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        WagesEntity wagesEntity = wagesService.addWages(wagesDao,authentication);
        if (ObjectUtil.isNotNull(wagesEntity)){
            return ResultUtil.success(wagesEntity);
        }
        return ResultUtil.faile("开工资失败");
    }
}
