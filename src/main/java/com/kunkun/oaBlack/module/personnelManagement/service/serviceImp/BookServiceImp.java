package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddBookDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.NoticeType;
import com.kunkun.oaBlack.module.personnelManagement.emum.book_status;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.*;
import com.kunkun.oaBlack.module.personnelManagement.mapper.BookMapper;
import com.kunkun.oaBlack.module.personnelManagement.mapper.NoticeMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.BookService;
import com.kunkun.oaBlack.module.personnelManagement.service.MeetingService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.vo.MeetingAndBookVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImp extends ServiceImpl<BookMapper, BookEntity> implements BookService {

    protected static final Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public NoticeEntity addBook(AddBookDao bookDao, Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity userEnity = personUserService.selectByIdMy(userId);
        UserEnity ruserEnity = personUserService.selectByIdMy(bookDao.getReviewerUserId());

        List<BookEntity> bookEntities = bookMapper.selectList(new LambdaQueryWrapper<BookEntity>()
                .ge(BookEntity::getBookStartTime,bookDao.getBookStartTime())
                .le(BookEntity::getBookEndTime,bookDao.getBookEndTime())
                .eq(BookEntity::getStatus,book_status.SUCCESS.getStatusCode())
        );
        int bookRow = -1;
        int noticeRow = -1;
        NoticeEntity noticeEntity = new NoticeEntity();


        if (bookEntities == null){
            BookEntity bookEntity = new BookEntity();
            bookEntity.setBookStartTime(new Date(bookDao.getBookStartTime()));
            bookEntity.setBookEndTime(new Date(bookDao.getBookEndTime()));
            bookEntity.setCreateUserId(userId);
            bookEntity.setCreateUserName(userEnity.getUserName());
            bookEntity.setMeetingDetails(bookDao.getMeetingDetails());
            bookEntity.setMeetingTitle(bookDao.getMeetingTitle());
            bookEntity.setReviewerUserId(bookDao.getReviewerUserId());
            bookEntity.setReviewerUserName(ruserEnity.getUserName());
            bookEntity.setStatus(book_status.FILE.getStatusCode());
            if (ObjectUtil.isNotNull(bookDao.getMembersIds())){
                bookEntity.setMembersIds(bookDao.getMembersIds());
            }
            bookEntity.setRoomId(bookDao.getRoomId());

            bookRow = bookMapper.insert(bookEntity);
            if(bookRow>0){
                logger.info("发起预约成功");
            }

            noticeEntity.setNoticeTitle(NoticeType.meetingApplication.getTypeName());
            noticeEntity.setCreateTime(new Date());
            noticeEntity.setNoticeType(NoticeType.meetingApplication.getTypeCode());
            noticeEntity.setSendUserId(userId);
            noticeEntity.setRecipientUserId(bookDao.getReviewerUserId());
            noticeEntity.setStatus(statusEmum.FILE.getStatusCode());
            noticeEntity.setIsDelete(0);
            noticeEntity.setOperationStatus(statusEmum.FILE.getStatusCode());
            noticeEntity.setEntityId(bookEntity.getBookId());
            noticeEntity.setEndTime(new Date(bookDao.getBookEndTime()));
            noticeRow = noticeMapper.insert(noticeEntity);
            if(noticeRow>0){
                logger.info("添加会议预约代办成功");
            }
        }else {
            logger.warn("预约失败，有人预约了");
        }

        if (bookRow>0 && noticeRow>0){
            return noticeEntity;
        }
        return null;
    }
}
