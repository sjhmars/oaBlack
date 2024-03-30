package com.kunkun.oaBlack.module.personnelManagement.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kunkun.oaBlack.common.util.BizException;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.CheckDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check")
@Api(tags = "打卡模块")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @ApiOperation("打卡")
    @PostMapping("/checkIn")
    public ResultUtil checkIn(@RequestBody String address){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        CheckEntity checkEntity = checkService.checkIn(authentication,address);
        if (ObjectUtil.isNull(checkEntity)){
            return ResultUtil.faile("打卡失败");
        }
        return ResultUtil.success("打卡成功",checkEntity);
    }

    @ApiOperation("一键创建空的打卡记录")
    @GetMapping("/createNullCheck")
    public ResultUtil createNullCheck(){
        try {
            checkService.doSomething();
        }catch (Exception e){
            return ResultUtil.faile(e.getMessage());
        }
        return ResultUtil.success("生成成功");
    }

    @ApiOperation("查询所有打卡记录")
    @PostMapping("/selectAllCheck")
    public ResultUtil selectAllCheck(@RequestBody CheckDao checkDao){
        IPage<CheckEntity> checkEntityPage = checkService.seleAllCheck(checkDao);
        if (checkEntityPage!=null){
            return ResultUtil.success("成功请求",checkEntityPage);
        }
        return ResultUtil.faile("失败了宝贝");
    }


    @ApiOperation("查询我自己的打卡记录")
    @PostMapping("/getMyCheck")
    public ResultUtil getMyCheck(@RequestBody CheckDao checkDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        IPage<CheckEntity> checkEntityPage = checkService.selectMyCheck(checkDao,authentication);
        if (checkEntityPage!=null){
            return ResultUtil.success("成功请求",checkEntityPage);
        }
        return ResultUtil.faile("失败了宝贝");
    }
}
