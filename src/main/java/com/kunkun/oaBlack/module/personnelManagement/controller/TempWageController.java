package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.PagesDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.TempWagesDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.WagePageDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.TempWagesEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.TempWagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/tempWage")
@Api(tags = "工资模块")
public class TempWageController {

    @Autowired
    private TempWagesService tempWagesService;

    @ApiOperation("设置模版weage")
    @PostMapping("/setTempWage")
    public ResultUtil setTempWage(TempWagesDao tempWagesDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(tempWagesService.setTempWages(authentication,tempWagesDao));
    }

    @ApiOperation("删除工资模版")
    @PostMapping("/deleteTempWage")
    public ResultUtil deleteTempWage(@RequestBody Integer tempWageId){
        TempWagesEntity tempWagesEntity = tempWagesService.getById(tempWageId);
        tempWagesEntity.setIsDelete(1);
        tempWagesEntity.setUpdateTime(new Date());
        if (tempWagesService.updateById(tempWagesEntity))
            return ResultUtil.success(tempWagesEntity);
        return ResultUtil.faile("删除失败");
    }

    @ApiOperation("更改工资模版")
    @PostMapping("/updateTempWage")
    public ResultUtil updateTempWage(@RequestBody TempWagesDao tempWagesDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        TempWagesEntity tempWagesEntity = tempWagesService.updateAllById(authentication,tempWagesDao);
        if (tempWagesEntity!=null){
            return ResultUtil.success(tempWagesEntity);
        }
        return ResultUtil.faile("更新失败");
    }

    @ApiOperation("查询所有工资模版")
    @PostMapping("/selectAllTempWage")
    public ResultUtil selectAllTempWage(PagesDao pagesDao){
        if (pagesDao.getPageSize()==null){
            pagesDao.setPageSize(10);
        }
        Page<TempWagesEntity> tempWagesEntityPage = new Page<>(pagesDao.getPageNumber(), pagesDao.getPageSize());
        return ResultUtil.success(tempWagesService.page(tempWagesEntityPage,new LambdaQueryWrapper<TempWagesEntity>().eq(TempWagesEntity::getIsDelete,0)));
    }

    @ApiOperation("根据用户id查询工资模板")
    @PostMapping("/selectAllTempWage")
    public ResultUtil selectTempWageById(WagePageDao wagePageDao){
        if (wagePageDao.getPageSize()==null){
            wagePageDao.setPageSize(10);
        }
        Page<TempWagesEntity> tempWagesEntityPage = new Page<>(wagePageDao.getPageNumber(), wagePageDao.getPageSize());
        return ResultUtil.success(tempWagesService.page(tempWagesEntityPage,new LambdaQueryWrapper<TempWagesEntity>()
                .eq(TempWagesEntity::getIsDelete,0).eq(TempWagesEntity::getUserId,wagePageDao.getUserId())));
    }
}
