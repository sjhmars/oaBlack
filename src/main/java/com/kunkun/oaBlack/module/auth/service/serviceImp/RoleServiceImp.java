package com.kunkun.oaBlack.module.auth.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.auth.enity.RoleEntity;
import com.kunkun.oaBlack.module.auth.mapper.RoleMapper;
import com.kunkun.oaBlack.module.auth.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}
