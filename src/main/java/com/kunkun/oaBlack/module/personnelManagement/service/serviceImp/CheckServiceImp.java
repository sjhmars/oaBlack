package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.module.personnelManagement.dao.CheckDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.CheckEntity;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.CheckMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.CheckService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
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

@Service
public class CheckServiceImp extends ServiceImpl<CheckMapper, CheckEntity> implements CheckService {

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private TokenStore jwtTokenStore;

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

        //记录当前事件，检查是否是上午
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
    public void balanceOneDay() {

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
            lastTime = hr + "时" + min + "分";
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
            earlyTime = hr + "时" + min + "分";
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
