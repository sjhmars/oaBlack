package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddDepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.DepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.service.DepartmentService;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Department")
@Api("部门管理模块")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("新增部门")
    @PostMapping("/addDepartment")
    public ResultUtil addDepartment(@Valid @RequestBody AddDepartmentDao addDepartmentDao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
            return ResultUtil.faile(errors);
        }
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return departmentService.addDepartment(addDepartmentDao,authentication);
    }

    @ApiOperation("返回部门目录")
    @GetMapping("/selectDepartmentTree")
    public ResultUtil selectDepartmentTree(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        List<DepartmentTreeVo>  departmentTreeVoList = departmentService.getDepartmentTree(authentication);
        return ResultUtil.success(departmentTreeVoList);
    }

    @ApiOperation("返回组织架构")
    @GetMapping("/selectDepartmentUserTree")
    public ResultUtil selectDepartmentUserTree(){
        List<DepartmentTreeUserVo> departmentTreeUserVos = departmentService.getDepartmentTreeUserVoTree();
        return ResultUtil.success(departmentTreeUserVos);
    }

}
