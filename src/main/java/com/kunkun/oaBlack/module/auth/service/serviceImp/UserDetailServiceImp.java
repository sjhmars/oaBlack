package com.kunkun.oaBlack.module.auth.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEnity userEnity = userMapper.selectOne(new LambdaQueryWrapper<UserEnity>().eq(UserEnity::getMobile,username));

        return null;
    }
}
