package com.kunkun.oaBlack.module.personnelManagement.controller;

import cn.hutool.core.util.ObjectUtil;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddUserDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.UpdateUserDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.UserDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.RoleEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.service.MyRoleService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.vo.UserAndDepartmentVo;
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

    @ApiOperation("根据Id查询角色")
    @GetMapping("/selectAllRole")
    private ResultUtil selectAllRole(){
        List<RoleEntity> roleEntityList = myRoleService.list();
        return ResultUtil.success(roleEntityList);
    }

    @ApiOperation("根据员工查询信息")
    @GetMapping("/selectPeopleById")
    private ResultUtil selectPeopleById(@RequestBody UserDao userDao){
        UserAndDepartmentVo userAndDepartmentVo = personUserService.selectUserById(userDao.getUserId());
        return ResultUtil.success(userAndDepartmentVo);
    }

    @ApiOperation("根据员工查询信息")
    @GetMapping("/selectAllPeople")
    private ResultUtil selectAllPeople(){
        List<UserAndDepartmentVo> userAndDepartmentVos = personUserService.selectAllPeople();
        return ResultUtil.success(userAndDepartmentVos);
    }

    @ApiOperation("更新员工信息")
    @PostMapping("/updateUser")
    public ResultUtil updateUser(@Valid @RequestBody UpdateUserDao updateUserDao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
            return ResultUtil.faile(errors);
        }
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserEnity userEnity = personUserService.updateUserById(updateUserDao,authentication);
        if (ObjectUtil.isNotNull(userEnity)){
            return ResultUtil.success(userEnity);
        }
        return ResultUtil.faile("更新失败");
    }
}
