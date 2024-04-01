package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.CheckDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.MakeUpCheckDao;
import com.kunkun.oaBlack.module.personnelManagement.emum.check_status;
import com.kunkun.oaBlack.module.personnelManagement.emum.statusEmum;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.LeaveEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.CheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import com.kunkun.oaBlack.module.personnelManagement.service.LeaveService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CheckServiceImp extends ServiceImpl<CheckMapper, CheckEntity> implements CheckService {

    protected static final Logger logger = LoggerFactory.getLogger(CheckServiceImp.class);

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private LeaveService leaveService;

    @Scheduled(cron = "0 0 1 * * 1-5")
    public void doSomething() {
        //首先查询出所有的员工
        List<UserEnity> userEnityList = personUserService.list(new LambdaQueryWrapper<UserEnity>().eq(UserEnity::getStatus,1));

        LocalDateTime thisDateZro = getThisDate();

        List<CheckEntity> checkEntities = checkMapper.selectList(new LambdaQueryWrapper<CheckEntity>().eq(CheckEntity::getThisDate,thisDateZro));
        if (CollectionUtils.isEmpty(checkEntities)){
            userEnityList.forEach( userEnity ->{
                Integer id = userEnity.getUserId();
                //创建一个考勤实体
                CheckEntity checkEntity = new CheckEntity();
                //设置参数
                checkEntity.setUserId(id);
                checkEntity.setThisDate(thisDateZro);
                checkEntity.setUserName(userEnity.getUserName());
                //查询当前用户是否在请假中
//            Leaves leaves = leavesService.queryIsLeaves(id, LocalDateTime.now());
//            if (ObjectUtil.isNotEmpty(leaves)){
//                attendanceRecords.setLeaveStatus(leaves.getLeaveType());
//            }else {
//                attendanceRecords.setLeaveStatus("正常");
//            }
//            //进行保存
//            add(attendanceRecords);
                checkMapper.insert(checkEntity);
            });
        }
    }

    @Override
    public IPage<CheckEntity> seleAllCheck(CheckDao checkDao) {
        if (checkDao.getPageSize() == null){
            checkDao.setPageSize(10);
        }
        Page<CheckEntity> checkEntityPage = new Page<>(checkDao.getPageNumber(),checkDao.getPageSize());
        IPage<CheckEntity> checkEntityIPage = null;
        if (checkDao.getStartTime()==null&&checkDao.getEndTime()==null){
            checkEntityIPage = checkMapper.selectPage(checkEntityPage,new LambdaQueryWrapper<CheckEntity>());
        }
        if (checkDao.getStartTime()!=null && checkDao.getEndTime()!=null){
            LocalDateTime startTime = Instant.ofEpochMilli(checkDao.getStartTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = Instant.ofEpochMilli(checkDao.getEndTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            checkEntityIPage = checkMapper.selectPage(checkEntityPage,new LambdaQueryWrapper<CheckEntity>()
                    .ge(CheckEntity::getThisDate,startTime)
                    .le(CheckEntity::getThisDate,endTime)
            );
        }
        return checkEntityIPage;
    }

    @Override
    public IPage<CheckEntity> seleCheckById(CheckDao checkDao) {
        if (checkDao.getPageSize() == null){
            checkDao.setPageSize(10);
        }
        Page<CheckEntity> checkEntityPage = new Page<>(checkDao.getPageNumber(),checkDao.getPageSize());
        IPage<CheckEntity> checkEntityIPage = null;
        if (checkDao.getStartTime()==null&&checkDao.getEndTime()==null){
            checkEntityIPage = checkMapper.selectPage(checkEntityPage,new LambdaQueryWrapper<CheckEntity>().eq(CheckEntity::getUserId,checkDao.getUserId()));
        }
        if (checkDao.getStartTime()!=null && checkDao.getEndTime()!=null){
            LocalDateTime startTime = Instant.ofEpochMilli(checkDao.getStartTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = Instant.ofEpochMilli(checkDao.getEndTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            checkEntityIPage = checkMapper.selectPage(checkEntityPage,new LambdaQueryWrapper<CheckEntity>()
                    .ge(CheckEntity::getThisDate,startTime)
                    .le(CheckEntity::getThisDate,endTime)
                    .eq(CheckEntity::getUserId,checkDao.getUserId())
            );
        }
        return checkEntityIPage;
    }

    @Override
    public IPage<CheckEntity> selectMyCheck(CheckDao checkDao,Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        if (checkDao.getPageSize() == null){
            checkDao.setPageSize(10);
        }
        Page<CheckEntity> checkEntityPage = new Page<>(checkDao.getPageNumber(),checkDao.getPageSize());
        IPage<CheckEntity> checkEntityIPage = null;
        if (checkDao.getStartTime()==null&&checkDao.getEndTime()==null){
            checkEntityIPage = checkMapper.selectPage(checkEntityPage,new LambdaQueryWrapper<CheckEntity>().eq(CheckEntity::getUserId,userId));
        }
        if (checkDao.getStartTime()!=null && checkDao.getEndTime()!=null){
            LocalDateTime startTime = Instant.ofEpochMilli(checkDao.getStartTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = Instant.ofEpochMilli(checkDao.getEndTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
            checkEntityIPage = checkMapper.selectPage(checkEntityPage,new LambdaQueryWrapper<CheckEntity>()
                    .ge(CheckEntity::getThisDate,startTime)
                    .le(CheckEntity::getThisDate,endTime)
                    .eq(CheckEntity::getUserId,userId)
            );
        }
        return checkEntityIPage;
    }

    @Override
    @Transactional
    public CheckEntity makeUpCheckIn(MakeUpCheckDao makeUpCheckDao) {

        LocalDateTime startTime = Instant.ofEpochMilli(makeUpCheckDao.getCheckStartTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
        LocalDateTime endTime = Instant.ofEpochMilli(makeUpCheckDao.getCheckEndTime()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();

        CheckEntity checkEntity = checkMapper.selectById(makeUpCheckDao.getCheckId());
        if (ObjectUtil.isNotNull(makeUpCheckDao.getCheckStartTime())){
            checkEntity.setCheckStartTime(startTime);
            checkEntity.setLateTime(null);
        }
        if (ObjectUtil.isNotNull(makeUpCheckDao.getCheckEndTime())){
            checkEntity.setCheckStartTime(endTime);
            checkEntity.setEarlyTime(null);
        }
        if (!Objects.equals(checkEntity.getLeaveStatus(), check_status.Holiday.getStatusNum())){
            logger.info("判断补卡后是否还是迟到");
            if (checkEntity.getLateTime()!=null&&checkEntity.getEarlyTime()!=null){
                checkEntity.setLeaveStatus(check_status.LateAndEarly.getStatusNum());
            }else if (checkEntity.getLateTime()!=null){
                checkEntity.setLeaveStatus(check_status.Late.getStatusNum());
            }else if (checkEntity.getEarlyTime()!=null){
                checkEntity.setLeaveStatus(check_status.Early_departure.getStatusNum());
            }else {
                checkEntity.setLeaveStatus(check_status.normal.getStatusNum());
            }
        }else {
            logger.info("请假状态还有迟到早退的");
            if (checkEntity.getLateTime()!=null&&checkEntity.getEarlyTime()!=null){
                checkEntity.setOtherStatus(check_status.LateAndEarly.getStatusNum());
            }else if (checkEntity.getLateTime()!=null){
                checkEntity.setOtherStatus(check_status.Late.getStatusNum());
            }else if (checkEntity.getEarlyTime()!=null){
                checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
            }else {
                checkEntity.setOtherStatus(check_status.normal.getStatusNum());
            }
        }
        int row = checkMapper.updateById(checkEntity);
        if (row>0){
            return checkEntity;
        }
        return null;
    }


    @Override
    @Transactional
    public CheckEntity checkIn(Authentication authentication, String address) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity userEnity = personUserService.selectByIdMy(userId);

        //设置今天0点（定时器启动失败时保底）
        LocalDateTime thisDate = getThisDate();

        CheckEntity check =  checkMapper.selectOne(new LambdaQueryWrapper<CheckEntity>().eq(CheckEntity::getUserId,userId).eq(CheckEntity::getThisDate,thisDate));

        //记录当前时间，检查是否是上午
        LocalDateTime checkTime = LocalDateTime.now();
        LocalDateTime givenDate = LocalDateTime.of(LocalDate.now(), LocalTime.NOON);
        int comparisonResult = checkTime.compareTo(givenDate);

        //判定是否已经有空白打卡记录，无则新建，随后判定早上晚上
        if (check == null){
            CheckEntity newCheck = new CheckEntity();
            newCheck.setThisDate(thisDate);
            newCheck.setUserId(userId);
            newCheck.setAddress(address);
            newCheck.setUserName(userEnity.getUserName());
            if (comparisonResult<0){
                //计算迟到(只算第一次卡)
                newCheck.setCheckStartTime(checkTime);
                long timestampInMillis = checkTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                String lateTime = checklast(timestampInMillis);
                if (lateTime!=null){
                    newCheck.setLateTime(lateTime);
                }
            }else {
                newCheck.setCheckEndTime(checkTime);
                long timestampInMillis = checkTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                String earlyTime = checkEarly(timestampInMillis);
                newCheck.setEarlyTime(earlyTime);
            }
            if (checkMapper.insert(newCheck)>0){
                return newCheck;
            }
        }else {
            check.setUserId(userId);
            check.setAddress(address);
            check.setUserName(userEnity.getUserName());
            if (comparisonResult<0){
                //计算迟到(只算第一次卡)
                if (check.getCheckStartTime() == null){
                    check.setCheckStartTime(checkTime);
                    //计算迟到
                    long timestampInMillis = checkTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    String lateTime = checklast(timestampInMillis);
                    if (lateTime!=null)
                        check.setLateTime(lateTime);
                }
            }else {
                check.setCheckEndTime(checkTime);
                //计算早退
                long timestampInMillis = checkTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                String earlyTime = checkEarly(timestampInMillis);
                check.setEarlyTime(earlyTime);
            }
            int rows = checkMapper.update(check, new LambdaUpdateWrapper<CheckEntity>()
                    .set(CheckEntity::getEarlyTime,check.getEarlyTime())
                    .eq(CheckEntity::getCheckId,check.getCheckId()));
            if (rows>0){
                return check;
            }
        }
        return null;
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 7 * * 1-5")
    public void balanceOneDay() {
        LocalDateTime thisTime = getThisDate();
        List<CheckEntity> checkEntityList = checkMapper.selectList(new LambdaQueryWrapper<CheckEntity>().eq(CheckEntity::getThisDate,thisTime));
        if (checkEntityList!=null){
            List<CheckEntity> aftwerBalance = checkEntityList.stream().map(checkEntity -> {
                if(checkEntity.getEarlyTime()==null&&checkEntity.getLateTime()==null){
                    checkEntity.setLeaveStatus(check_status.normal.getStatusNum());
                }
                if (checkEntity.getCheckStartTime() == null || checkEntity.getCheckEndTime() == null){
                    checkEntity.setLeaveStatus(check_status.Absenteeism.getStatusNum());
                }
                if (checkEntity.getLateTime()!=null&&checkEntity.getEarlyTime()==null){
                    checkEntity.setLeaveStatus(check_status.Late.getStatusNum());
                }
                if (checkEntity.getLateTime()==null&&checkEntity.getEarlyTime()!=null){
                    checkEntity.setLeaveStatus(check_status.Early_departure.getStatusNum());
                }
                if (checkEntity.getLateTime()!=null&&checkEntity.getEarlyTime()!=null){
                    checkEntity.setLeaveStatus(check_status.LateAndEarly.getStatusNum());
                }
                LeaveEntity leaveEntity = leaveService.getOne(new LambdaQueryWrapper<LeaveEntity>()
                        .eq(LeaveEntity::getCreateUserId,checkEntity.getUserId())
                        .eq(LeaveEntity::getStatus, statusEmum.SUCCESS.getStatusCode())
                        .ge(LeaveEntity::getEndTime,thisTime)
                );
                //请假情况判断，我不确定正不正确
                if (leaveEntity!=null){

                    //假期期间重新判断迟到时间
                    checkEntity.setEarlyTime(null);
                    checkEntity.setLateTime(null);

                    long beginTime = leaveEntity.getBeginTime().getTime();
                    logger.info( beginTime + "请假开始时间");
                    long endTime = leaveEntity.getEndTime().getTime();
                    logger.info( endTime + "请假结束");

                    LocalDateTime Nine = LocalDateTime.now().with(LocalTime.of(9, 0));
                    long NineInMillis = Nine.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    LocalDateTime noon = LocalDateTime.now().with(LocalTime.of(12, 0));
                    long noonMillis = noon.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    LocalDateTime halfSix = LocalDateTime.now().with(LocalTime.of(18, 30));
                    long HalfSixInMillis = halfSix.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    //开始时间在今天之前
                    if (beginTime<=NineInMillis){
                        logger.info("在今天之前开始请假的情况");
                        if (endTime<HalfSixInMillis && endTime>=noonMillis){
                            logger.info("结束时间在今天6.30之前，中午之后");
                            if (checkEntity.getCheckEndTime()!=null){
                                checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                                long checkET = checkEntity.getCheckEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                if (checkET<endTime){
                                    String earlyTime = checkEarly(endTime);
                                    if (earlyTime!=null){
                                        logger.info("请假结束后未及时打卡签退");
                                        checkEntity.setEarlyTime(earlyTime);
                                        checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                    }
                                }
                            }else {
                                logger.info("没有endTime直接旷工");
                                checkEntity.setLeaveStatus(check_status.Absenteeism.getStatusNum());
                            }
                        }
                        if (endTime<noonMillis){
                            if (checkEntity.getCheckEndTime()!=null){
                                checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                                if (checkEntity.getCheckStartTime()!=null){
                                    long checkST = checkEntity.getCheckStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                    long checkET = checkEntity.getCheckEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                    if (endTime < checkST){
                                        logger.info("时间没错呀");
                                        long reduce = checkST-endTime;
                                        double hr = Math.floor(reduce / 3600000 % 24);
                                        reduce = reduce - (long)(hr * 3600000 );
                                        // 分钟
                                        double min = Math.floor(reduce / 60000 % 60);
                                        String lastTime = hr + "时" + min;
                                        checkEntity.setLateTime(lastTime);
                                        checkEntity.setOtherStatus(check_status.Late.getStatusNum());
                                    }
                                    if (checkET<HalfSixInMillis){
                                        String ETime = checkEarly(checkET);
                                        if (ETime!=null&&checkEntity.getLateTime()!=null){
                                            checkEntity.setEarlyTime(ETime);
                                            checkEntity.setOtherStatus(check_status.LateAndEarly.getStatusNum());
                                        }else if (ETime!=null){
                                            checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                        }
                                    }
                                }else {
                                    logger.info("没打上午卡，直接旷工");
                                    checkEntity.setLeaveStatus(check_status.Absenteeism.getStatusNum());
                                }
                            }else {
                                logger.info("没有打晚上的卡，直接旷工");
                                checkEntity.setLeaveStatus(check_status.Absenteeism.getStatusNum());
                            }
                        }
                        if (endTime==HalfSixInMillis){
                            checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                        }
                        if (endTime>=HalfSixInMillis){
                            checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                        }
                    }
                    //开始时间在今天
                    if (beginTime>NineInMillis){
                        if (beginTime<noonMillis){
                            if (checkEntity.getCheckStartTime()!=null){
                                long checkST = checkEntity.getCheckStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                if (endTime < checkST){
                                    logger.info("请假后还是迟到了");
                                    checkEntity.setOtherStatus(check_status.Late.getStatusNum());
                                    long reduce = checkST-endTime;
                                    double hr = Math.floor(reduce / 3600000 % 24);
                                    reduce = reduce - (long)(hr * 3600000 );
                                    // 分钟
                                    double min = Math.floor(reduce / 60000 % 60);
                                    String lastTime = hr + "时" + min;
                                    checkEntity.setLateTime(lastTime);
                                }
                                if (endTime<HalfSixInMillis&&endTime>noonMillis){
                                    if (checkEntity.getCheckEndTime()!=null){
                                        checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                                        long endST = checkEntity.getCheckEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                        String earlyTime;
                                        if (endST>endTime){
                                            logger.info("最后打卡时间大于请假时间，按照最后打卡时间算早退");
                                            earlyTime = checkEarly(endST);
                                            if (earlyTime!=null){
                                                checkEntity.setEarlyTime(earlyTime);
                                                if (checkEntity.getLateTime()!=null){
                                                    checkEntity.setOtherStatus(check_status.LateAndEarly.getStatusNum());
                                                }else {
                                                    checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                                }
                                            }
                                        }else if (endST<endTime){
                                            logger.info("最后打卡时间小于请假时间，按照请假时间算早退");
                                            earlyTime = checkEarly(endTime);
                                            if (earlyTime!=null){
                                                checkEntity.setEarlyTime(earlyTime);
                                                if (checkEntity.getLateTime()!=null){
                                                    checkEntity.setOtherStatus(check_status.LateAndEarly.getStatusNum());
                                                }else {
                                                    checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                                }
                                            }
                                        }
                                    }
                                }
                                if (endTime<=noonMillis){
                                    logger.info("只请假上午一点点时间的那种");
                                    checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                                    long endT = checkEntity.getCheckEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                    String earlyT = checkEarly(endT);
                                    if (checkEntity.getCheckEndTime()==null){
                                        checkEntity.setOtherStatus(check_status.Absenteeism.getStatusNum());
                                    }else if (earlyT!=null&&checkEntity.getLateTime()!=null){
                                        checkEntity.setEarlyTime(earlyT);
                                        checkEntity.setOtherStatus(check_status.LateAndEarly.getStatusNum());
                                    }else if (earlyT!=null){
                                        checkEntity.setEarlyTime(earlyT);
                                        checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                    }
                                }
                                if (endTime>HalfSixInMillis){
                                    logger.info("这小子晚上不用打卡1");
                                    checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                                }
                            }else if (checkEntity.getCheckStartTime()==null){
                                checkEntity.setOtherStatus(check_status.Absenteeism.getStatusNum());
                            }
                        }
                        if (beginTime>=noonMillis){
                            if (endTime<=HalfSixInMillis){
                                checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                                if (checkEntity.getCheckEndTime()!=null){
                                    long endST = checkEntity.getCheckEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                    String earlyTime;
                                    logger.info("最后打卡时间大于请假时间，按照最后打卡时间算早退");
                                    if (endST>endTime){
                                        earlyTime = checkEarly(endST);
                                        if (earlyTime!=null){
                                            checkEntity.setEarlyTime(earlyTime);
                                            checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                        }
                                    }
                                    if (endST<endTime){
                                        logger.info("最后打卡时间小于请假时间，按照请假时间算早退");
                                        earlyTime = checkEarly(endTime);
                                        if (earlyTime!=null){
                                            checkEntity.setEarlyTime(earlyTime);
                                            checkEntity.setOtherStatus(check_status.Early_departure.getStatusNum());
                                        }
                                    }
                                }
                            }
                            if (endTime>HalfSixInMillis){
                                logger.info("这小子晚上不用打卡");
                                checkEntity.setLeaveStatus(check_status.Holiday.getStatusNum());
                            }
                            // 需要判断早上有没有打卡
                            if (checkEntity.getCheckStartTime()!=null){
                                logger.info("早上打卡了");
                                long STime = checkEntity.getCheckStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                String ltime = checklast(STime);
                                if (ltime!=null){
                                    logger.info("但是迟到了");
                                    checkEntity.setLateTime(ltime);
                                    if (checkEntity.getEarlyTime()!=null){
                                        checkEntity.setOtherStatus(check_status.LateAndEarly.getStatusNum());
                                    }else {
                                        checkEntity.setOtherStatus(check_status.Late.getStatusNum());
                                    }
                                }
                            }else if (checkEntity.getCheckStartTime()==null){
                                logger.info("早上没打卡");
                                checkEntity.setOtherStatus(check_status.Absenteeism.getStatusNum());
                            }
                        }
                    }
                }
                return checkEntity;
            }).collect(Collectors.toList());
            for (CheckEntity afterCheckEntity:aftwerBalance) {
                checkMapper.updateById(afterCheckEntity);
            }
        }
    }

    public String checklast(long timestampInMillis){

        LocalDateTime Nine = LocalDateTime.now().with(LocalTime.of(9, 0));
        long NineInMillis = Nine.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        String lastTime;
        if (timestampInMillis>NineInMillis){
            long reduce = timestampInMillis-NineInMillis;
            double hr = Math.floor(reduce / 3600000 % 24);
            reduce = reduce - (long)(hr * 3600000 );
            // 分钟
            double min = Math.floor(reduce / 60000 % 60);
            lastTime = hr + "时" + min;
            return lastTime;
        }
        return null;
    }

    public String checkEarly(long timestampInMillis){

        LocalDateTime halfSix = LocalDateTime.now().with(LocalTime.of(18, 30));
        long HalfSixInMillis = halfSix.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String earlyTime;
        if (timestampInMillis<HalfSixInMillis){
            long reduce = HalfSixInMillis-timestampInMillis;
            double hr = Math.floor(reduce / 3600000 % 24);
            reduce = reduce - (long)(hr * 3600000 );
            // 分钟
            double min = Math.floor(reduce / 60000 % 60);
            earlyTime = hr + "时" + min;
            return earlyTime;
        }
        return null;
    }

    private LocalDateTime getThisDate(){
        //        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY,0);
//        calendar.set(Calendar.MINUTE,0);
//        calendar.set(Calendar.SECOND,0);
//        LocalDateTime.now().with(LocalTime.of(9, 0));
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

//    public static void main(String[] args) {
//        System.out.println(LocalDateTime.now().with(LocalTime.of(12, 0,0)));
//        System.out.println(new CheckServiceImp().getThisDate());
//    }
}
