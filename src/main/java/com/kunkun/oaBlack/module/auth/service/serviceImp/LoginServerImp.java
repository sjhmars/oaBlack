package com.kunkun.oaBlack.module.auth.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.auth.enity.UserEnity;
import com.kunkun.oaBlack.module.auth.mapper.UserMapper;
import com.kunkun.oaBlack.module.auth.service.LoginServer;
import org.springframework.stereotype.Service;

@Service
public class LoginServerImp extends ServiceImpl<UserMapper, UserEnity> implements LoginServer {
}
