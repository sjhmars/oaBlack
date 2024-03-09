package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.mapper.PersonUserMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonPersonUserServiceImp extends ServiceImpl<PersonUserMapper, UserEnity> implements PersonUserService {

    @Autowired
    private PersonUserMapper personUserMapper;

    @Cacheable(value = "user", key = "#userId")
    @Override
    public UserEnity selectById(Integer userId) {
        return personUserMapper.selectById(userId);
    }
}
