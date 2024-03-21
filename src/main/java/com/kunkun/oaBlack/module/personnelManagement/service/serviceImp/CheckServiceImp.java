package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.CheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import org.springframework.stereotype.Service;

@Service
public class CheckServiceImp extends ServiceImpl<CheckMapper, CheckEntity> implements CheckService {
}
