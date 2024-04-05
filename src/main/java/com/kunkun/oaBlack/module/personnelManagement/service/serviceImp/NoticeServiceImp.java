package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.NoticePageDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.NoticeType;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.BookEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.NoticeEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.SupplementCheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.*;
import com.kunkun.oaBlack.module.personnelManagement.vo.AllWageVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.NoticeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImp extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {

    protected static final Logger logger = LoggerFactory.getLogger(NoticeServiceImp.class);

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private SupplementCheckService supplementCheckService;

    @Autowired
    private CheckService checkService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    @Transactional
    public NoticeEntity agree(Integer noticeId,String noticeContent) {
        NoticeEntity noticeEntity = noticeMapper.selectById(noticeId);
        Date thisDate = new Date();
        int row;
        if (thisDate.getTime()>noticeEntity.getEndTime().getTime()){
            noticeEntity.setOperationStatus(statusEmum.AUDITINGFILE.getStatusCode());
            noticeEntity.setNoticeContent("已过期，无法同意");
            row = noticeMapper.update(noticeEntity,new LambdaUpdateWrapper<NoticeEntity>()
                    .eq(NoticeEntity::getNoticeId,noticeId)
                    .eq(NoticeEntity::getIsDelete,0)
            );
            if (row>0)
                return noticeEntity;
        }
        if (thisDate.getTime()<noticeEntity.getEndTime().getTime()){
            if (noticeEntity.getNoticeType().equals(NoticeType.holidayReview.getTypeCode())){
                LeaveEntity leaveEntity = leaveService.getById(noticeEntity.getEntityId());
                leaveEntity.setStatus(statusEmum.SUCCESS.getStatusCode());
                if (leaveService.update(leaveEntity,new LambdaUpdateWrapper<LeaveEntity>().eq(LeaveEntity::getLeaveId,leaveEntity.getLeaveId()))){
                    logger.info("请假成功");
                }
                else {
                    return null;
                }
            }
            if (noticeEntity.getNoticeType().equals(NoticeType.cardReplacement.getTypeCode())){
                SupplementCheckEntity supplementCheckEntity = supplementCheckService.getById(noticeEntity.getEntityId());
                supplementCheckEntity.setStatus(statusEmum.SUCCESS.getStatusCode());
                if (supplementCheckService.update(supplementCheckEntity,new LambdaUpdateWrapper<SupplementCheckEntity>().eq(SupplementCheckEntity::getSupplementId,supplementCheckEntity.getSupplementId()))){
                    checkService.makeUpCheckIn(supplementCheckEntity);
                    logger.info("补卡成功");
                }
                else {
                    return null;
                }
            }
            if (noticeEntity.getNoticeType().equals(NoticeType.meetingApplication.getTypeCode())){
                BookEntity bookEntity = bookService.getById(noticeEntity.getEntityId());
                bookEntity.setStatus(statusEmum.SUCCESS.getStatusCode());
                if (bookService.update(bookEntity,new LambdaUpdateWrapper<BookEntity>().eq(BookEntity::getBookId,bookEntity.getBookId()))){
                    logger.info("预约成功");
                }
                else {
                    return null;
                }
            }
            noticeEntity.setOperationStatus(statusEmum.SUCCESS.getStatusCode());
            noticeEntity.setNoticeContent(noticeContent);
            row = noticeMapper.update(noticeEntity,new LambdaUpdateWrapper<NoticeEntity>()
                    .eq(NoticeEntity::getNoticeId,noticeId)
                    .eq(NoticeEntity::getIsDelete,0)
            );
            if (row>0){
                logger.info("审批通过成功");
                return noticeEntity;
            }
        }
        return null;
    }

    @Override
    public IPage<NoticeVo> selectNoticeAuditingPage(NoticePageDao noticePageDao) {
        if (noticePageDao.getPageSize() == null){
            noticePageDao.setPageSize(10);
        }
        Page<NoticeEntity> page = new Page<>(noticePageDao.getPageNumber(),noticePageDao.getPageSize());
        IPage<NoticeEntity> noticeEntityIPage = noticeMapper.selectPage(page,new LambdaQueryWrapper<NoticeEntity>().eq(NoticeEntity::getRecipientUserId,noticePageDao.getUserId()));
        List<NoticeEntity> noticeEntities = noticeEntityIPage.getRecords();
        List<NoticeVo> noticeVos = noticeEntities.stream().map(noticeEntity -> {
            NoticeVo noticeVo = new NoticeVo();
            noticeVo.setNoticeId(noticeEntity.getNoticeId());
            noticeVo.setCreateTime(noticeEntity.getCreateTime());
            noticeVo.setEndTime(noticeEntity.getEndTime());
            noticeVo.setEntityId(noticeEntity.getEntityId());
            noticeVo.setNoticeContent(noticeEntity.getNoticeContent());
            noticeVo.setNoticeTitle(noticeEntity.getNoticeTitle());
            noticeVo.setNoticeType(noticeEntity.getNoticeType());
            if (noticeVo.getNoticeType().equals(NoticeType.holidayReview.getTypeCode())){
                noticeVo.setEntity(leaveService.getById(noticeVo.getEntityId()));
            }
            if (noticeVo.getNoticeType().equals(NoticeType.cardReplacement.getTypeCode())){
                noticeVo.setEntity(supplementCheckService.getById(noticeVo.getEntityId()));
            }
            if (noticeVo.getNoticeType().equals(NoticeType.meetingApplication.getTypeCode())){
                noticeVo.setEntity(bookService.getById(noticeVo.getEntityId()));
            }
            noticeVo.setOperationStatus(noticeEntity.getOperationStatus());
            return noticeVo;
        }).collect(Collectors.toList());
        IPage<NoticeVo> noticeVoIPage = new Page<>();
        noticeVoIPage.setCurrent(noticeEntityIPage.getCurrent());
        noticeVoIPage.setRecords(noticeVos);
        noticeVoIPage.setPages(noticeEntityIPage.getPages());
        noticeVoIPage.setSize(noticeEntityIPage.getSize());
        noticeVoIPage.setTotal(noticeEntityIPage.getTotal());
        return noticeVoIPage;
    }

    @Override
    public IPage<NoticeVo> selectNoticeApplicationPage(Authentication authentication,NoticePageDao noticePageDao) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        if (noticePageDao.getPageSize() == null){
            noticePageDao.setPageSize(10);
        }
        Page<NoticeEntity> page = new Page<>(noticePageDao.getPageNumber(),noticePageDao.getPageSize());
        IPage<NoticeEntity> noticeEntityIPage = noticeMapper.selectPage(page,new LambdaQueryWrapper<NoticeEntity>().eq(NoticeEntity::getSendUserId,userId));
        List<NoticeEntity> noticeEntities = noticeEntityIPage.getRecords();
        List<NoticeVo> noticeVos = noticeEntities.stream().map(noticeEntity -> {
            NoticeVo noticeVo = new NoticeVo();
            noticeVo.setNoticeId(noticeEntity.getNoticeId());
            noticeVo.setCreateTime(noticeEntity.getCreateTime());
            noticeVo.setEndTime(noticeEntity.getEndTime());
            noticeVo.setEntityId(noticeEntity.getEntityId());
            noticeVo.setNoticeContent(noticeEntity.getNoticeContent());
            noticeVo.setNoticeTitle(noticeEntity.getNoticeTitle());
            noticeVo.setNoticeType(noticeEntity.getNoticeType());
            if (noticeVo.getNoticeType().equals(NoticeType.holidayReview.getTypeCode())){
                noticeVo.setEntity(leaveService.getById(noticeVo.getEntityId()));
            }
            if (noticeVo.getNoticeType().equals(NoticeType.cardReplacement.getTypeCode())){
                noticeVo.setEntity(leaveService.getById(noticeVo.getEntityId()));
            }
            noticeVo.setOperationStatus(noticeEntity.getOperationStatus());
            return noticeVo;
        }).collect(Collectors.toList());
        IPage<NoticeVo> noticeVoIPage = new Page<>();
        noticeVoIPage.setCurrent(noticeEntityIPage.getCurrent());
        noticeVoIPage.setRecords(noticeVos);
        noticeVoIPage.setPages(noticeEntityIPage.getPages());
        noticeVoIPage.setSize(noticeEntityIPage.getSize());
        noticeVoIPage.setTotal(noticeEntityIPage.getTotal());
        return noticeVoIPage;
    }

    @Override
    public Integer selectNoReadNotice(Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        return noticeMapper.selectNoReadNum(userId);
    }


}
