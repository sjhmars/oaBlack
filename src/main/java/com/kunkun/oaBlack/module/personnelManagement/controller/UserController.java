package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddUserDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.RoleEntity;
import com.kunkun.oaBlack.module.personnelManagement.service.MyRoleService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
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
@RequestMapping("/user")
@Api("用户模块")
public class UserController {
    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private MyRoleService myRoleService;

    @ApiOperation("新增用户")
    @PostMapping("/addUser")
    public ResultUtil addNewUser(@Valid @RequestBody AddUserDao addUserDao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
            return ResultUtil.faile(errors);
        }
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return personUserService.addUser(addUserDao,authentication);
    }

    @ApiOperation("查询所有角色")
    @GetMapping("/selectAllRole")
    private ResultUtil selectAllRole(){
        List<RoleEntity> roleEntityList = myRoleService.list();
        return ResultUtil.success(roleEntityList);
    }
}
