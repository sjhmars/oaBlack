package com.kunkun.oaBlack.module.auth.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kunkun.oaBlack.module.auth.dao.LoginDao;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import com.kunkun.oaBlack.module.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEnity userEnity = userService.getOne(new LambdaQueryWrapper<UserEnity>().eq(UserEnity::getMobile,username));
        LoginDao loginUser = new LoginDao();
        if (ObjectUtil.isEmpty(userEnity)){
            throw new RuntimeException("用户不存在");
        }
        if (userEnity.getStatus()==0){
            loginUser.set_delete(false);
        }
        loginUser.setUserEnity(userEnity);
        String permission = userEnity.getRoleName();
        List<String> permissions = Stream.of(permission.split(":")).collect(Collectors.toList()); //获取用户权限
        loginUser.setPermissions(permissions);
        return loginUser;
    }
}
