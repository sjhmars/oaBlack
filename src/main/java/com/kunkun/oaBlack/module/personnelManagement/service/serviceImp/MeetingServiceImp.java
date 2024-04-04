package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.MeetingDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.MeetingMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingServiceImp extends ServiceImpl<MeetingMapper, MeetingEntity> implements MeetingService {

    @Autowired
    private MeetingMapper meetingMapper;

    @Override
    @Transactional
    public MeetingEntity addMeeting(MeetingDao meetingDao) {
        MeetingEntity meetingEntity = new MeetingEntity();
        meetingEntity.setIsDelete(0);
        meetingEntity.setRoomFloor(meetingDao.getRoomFloor());
        meetingEntity.setRoomIdentifier(meetingDao.getRoomIdentifier());
        meetingEntity.setRoomName(meetingDao.getRoomName());
        meetingEntity.setRoomSize(meetingDao.getRoomSize());
        int row = meetingMapper.insert(meetingEntity);
        if (row>0){
            return meetingEntity;
        }
        return null;
    }

    @Override
    @Transactional
    public ResultUtil deleteMeeting(Integer roomId) {
        MeetingEntity meetingEntity = meetingMapper.selectOne(new LambdaQueryWrapper<MeetingEntity>()
                .eq(MeetingEntity::getRoomId,roomId)
                .eq(MeetingEntity::getIsDelete,0)
        );
        meetingEntity.setIsDelete(1);
        int row = meetingMapper.update(meetingEntity,new LambdaUpdateWrapper<MeetingEntity>().eq(MeetingEntity::getRoomId,roomId));
        if (row>0){
            return ResultUtil.success("删除成功",meetingEntity);
        }
        return ResultUtil.faile("删除失败");
    }
}
