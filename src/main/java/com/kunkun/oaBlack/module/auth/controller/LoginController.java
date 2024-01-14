package com.kunkun.oaBlack.module.auth.controller;

import com.kunkun.oaBlack.module.auth.dao.RegistDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Api(tags = "登录认证模块")
public class LoginController {

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

    @PostMapping("/login")
    public String login(){
        return null;
    }
}
