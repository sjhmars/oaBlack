package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.RoleEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.MyRoleMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.MyRoleService;
import org.springframework.stereotype.Service;

@Service
public class MyRoleServiceImp extends ServiceImpl<MyRoleMapper, RoleEntity> implements MyRoleService {
}
