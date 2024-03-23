package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.LeaveMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImp extends ServiceImpl<LeaveMapper, LeaveEntity> implements LeaveService {
}
