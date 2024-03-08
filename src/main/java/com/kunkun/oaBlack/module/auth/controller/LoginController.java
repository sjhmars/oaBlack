package com.kunkun.oaBlack.module.auth.controller;

import com.kunkun.oaBlack.common.util.BizException;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.auth.dao.RegistDao;
import com.kunkun.oaBlack.module.auth.dao.UserDao;
import com.kunkun.oaBlack.module.auth.service.LoginServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Api(tags = "登录认证模块")
public class LoginController {

    @Autowired
    public LoginServer loginService;

    @ApiOperation(value = "测试模块")
    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @ApiOperation("注册测试用")
    @PostMapping("/regist")
    public String regist(@RequestBody RegistDao registDao){
        return null;
    }

    /**
     *
     * @param userDao 传入dao
     * @param bindingResult 直接传异常给前端
     * @return
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultUtil login(@Valid @RequestBody UserDao userDao, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,String> errors = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
            return ResultUtil.faile(errors);
        }
        return loginService.login(userDao);
    }

    @ApiOperation("退出登录")
    @PostMapping("/loginOut")
    public ResultUtil loginOut(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return loginService.loginOut(authentication);
    }
}
