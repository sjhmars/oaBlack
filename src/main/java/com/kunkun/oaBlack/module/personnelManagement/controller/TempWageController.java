package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.TempWagesDao;
import com.kunkun.oaBlack.module.personnelManagement.service.TempWagesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tempWage")
@Api(tags = "工资模块")
public class TempWageController {

    @Autowired
    private TempWagesService tempWagesService;

    @PostMapping("/setTempWage")
    public ResultUtil setTempWage(TempWagesDao tempWagesDao){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(tempWagesService.setTempWages(authentication,tempWagesDao));
    }
}
