package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.MeetingDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.book_status;
import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.MeetingEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.BookMapper;
import com.kunkun.oaBlack.module.personnelManagement.mapper.MeetingMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.MeetingService;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingAndBookVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImp extends ServiceImpl<MeetingMapper, MeetingEntity> implements MeetingService {

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TokenStore jwtTokenStore;

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

    @Override
    public List<MeetingListVo> selectAllList() {
        Date firstDay = getMonthFirst().getTime();
        Date endDay = getMonthEnd().getTime();

        List<MeetingEntity> meetingEntities = this.list();
        List<MeetingListVo> meetingListVos = meetingEntities.stream().map(meetingEntity -> {
            List<BookEntity> bookEntities = bookMapper.selectList(new LambdaQueryWrapper<BookEntity>()
                    .eq(BookEntity::getRoomId,meetingEntity.getRoomId())
                    .ge(BookEntity::getBookStartTime,firstDay)
                    .le(BookEntity::getBookEndTime,endDay)
                    .eq(BookEntity::getStatus, book_status.SUCCESS.getStatusCode())
            );
            MeetingListVo meetingListVo = new MeetingListVo();
            meetingListVo.setRoomId(meetingEntity.getRoomId());
            meetingListVo.setRoomFloor(meetingEntity.getRoomFloor());
            meetingListVo.setRoomIdentifier(meetingEntity.getRoomIdentifier());
            meetingListVo.setRoomName(meetingEntity.getRoomName());
            meetingListVo.setRoomSize(meetingEntity.getRoomSize());
            meetingListVo.setBookEntities(bookEntities);
            return meetingListVo;
        }).collect(Collectors.toList());
        return meetingListVos;
    }

    @Override
    public MeetingListVo selectAllListById(Integer roomId) {
        Date firstDay = getMonthFirst().getTime();
        Date endDay = getMonthEnd().getTime();

        MeetingEntity meetingEntity = meetingMapper.selectById(roomId);
        MeetingListVo meetingListVo = new MeetingListVo();
        meetingListVo.setRoomId(meetingEntity.getRoomId());
        meetingListVo.setRoomFloor(meetingEntity.getRoomFloor());
        meetingListVo.setRoomIdentifier(meetingEntity.getRoomIdentifier());
        meetingListVo.setRoomName(meetingEntity.getRoomName());
        meetingListVo.setRoomSize(meetingEntity.getRoomSize());
        List<BookEntity> bookEntities = bookMapper.selectList(new LambdaQueryWrapper<BookEntity>()
                .eq(BookEntity::getRoomId,meetingEntity.getRoomId())
                .ge(BookEntity::getBookStartTime,firstDay)
                .le(BookEntity::getBookEndTime,endDay)
                .eq(BookEntity::getStatus, book_status.SUCCESS.getStatusCode())
        );
        meetingListVo.setBookEntities(bookEntities);
        return meetingListVo;
    }

    @Override
    public List<MeetingListVo> selectAMyList(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        Date firstDay = getMonthFirst().getTime();
        Date endDay = getMonthEnd().getTime();

        List<MeetingEntity> meetingEntities = this.list();
        List<MeetingListVo> meetingListVos = meetingEntities.stream().map(meetingEntity -> {
            List<BookEntity> bookEntities = bookMapper.selectList(new LambdaQueryWrapper<BookEntity>()
                    .eq(BookEntity::getRoomId,meetingEntity.getRoomId())
                    .ge(BookEntity::getBookStartTime,firstDay)
                    .le(BookEntity::getBookEndTime,endDay)
                    .eq(BookEntity::getStatus, book_status.SUCCESS.getStatusCode())
                    .eq(BookEntity::getCreateUserId,userId)
            );
            MeetingListVo meetingListVo = new MeetingListVo();
            meetingListVo.setRoomId(meetingEntity.getRoomId());
            meetingListVo.setRoomFloor(meetingEntity.getRoomFloor());
            meetingListVo.setRoomIdentifier(meetingEntity.getRoomIdentifier());
            meetingListVo.setRoomName(meetingEntity.getRoomName());
            meetingListVo.setRoomSize(meetingEntity.getRoomSize());
            meetingListVo.setBookEntities(bookEntities);
            return meetingListVo;
        }).collect(Collectors.toList());
        return meetingListVos;
    }

    private Calendar getMonthFirst(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar;
    }

    private Calendar getMonthEnd(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }
}
