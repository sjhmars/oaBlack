package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImp extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {
}
