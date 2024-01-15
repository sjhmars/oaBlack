package com.kunkun.oaBlack.module.auth.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import io.swagger.models.auth.In;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginDao implements UserDetails {

    private UserEnity userEnity;

    private List<String> permissions;

    private boolean is_delete = true;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginDao(UserEnity userPojo, List<String> permissions) {
        this.userEnity = userPojo;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> list = permissions.stream().map(permission->{
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//            return authority;
//        }).collect(Collectors.toList());
// 是我写长了
        if (authorities!=null){
            return authorities;
        }
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());//因为SimpleGrantedAuthority只有一个参数的构造方法可直接new
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEnity.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userEnity.getMobile();
    }

    public Integer getId(){
        return userEnity.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return is_delete;
    }
}
