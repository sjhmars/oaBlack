package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.auth.mapper.RoleMapper;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddUserDao;
import com.kunkun.oaBlack.module.personnelManagement.dao.UpdateUserDao;
import com.kunkun.oaBlack.module.personnelManagement.mapper.MyRoleMapper;
import com.kunkun.oaBlack.module.personnelManagement.mapper.PersonUserMapper;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.mapper.PostMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.DepartmentService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.vo.DUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.UserAndDepartmentVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonPersonUserServiceImp extends ServiceImpl<PersonUserMapper, UserEnity> implements PersonUserService {

    @Autowired
    private PersonUserMapper personUserMapper;

    @Autowired
    private MyRoleMapper roleMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TokenStore jwtTokenStore;

    @Cacheable(value = "user", key = "#userId")
    @Override
    public UserEnity selectByIdMy(Integer userId) {
        System.out.println(userId);
        return personUserMapper.selectById(userId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "DepartmentUserTree",allEntries=true)
    public ResultUtil addUser(AddUserDao addUserDao,Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");
        UserEnity userEnity = new UserEnity();
        userEnity.setCreateTime(new Date());
        UserEnity CreatUser = selectByIdMy(userId);
        userEnity.setCreateBy(CreatUser.getUserName());
        userEnity.setUserName(addUserDao.getUserName());
        userEnity.setSex(addUserDao.getSex());
        userEnity.setBirth(addUserDao.getBirth());
        userEnity.setMobile(addUserDao.getMobile());
        userEnity.setWorkStatus(addUserDao.getWorkStatus());
        userEnity.setDepartmentId(addUserDao.getDepartmentId());
        userEnity.setPostId(addUserDao.getPostId());
        userEnity.setStatus(1);

        String roleName =  roleMapper.selectRoleName(addUserDao.getRoleId());
        userEnity.setRoleId(addUserDao.getRoleId());
        userEnity.setRoleName(roleName);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncoder =  bCryptPasswordEncoder.encode(addUserDao.getUserPassword());
        userEnity.setUserPassword(passwordEncoder);
        try {
            if (personUserMapper.insert(userEnity)>0){
                return ResultUtil.success(userEnity);
            }
        }catch (Exception e){
            String errMsg = e.getMessage();
            if (StrUtil.isNotBlank(errMsg)&&errMsg.contains("user.one")){
                return ResultUtil.faile("用户id唯一");
            }
            if (StrUtil.isNotBlank(errMsg)&&errMsg.contains("user.phoneOne"))
                return ResultUtil.faile("用户手机不唯一");
        }
        return ResultUtil.faile("插入失败原因不详");
    }

    @Override
    public UserAndDepartmentVo selectUserById(Integer userId) {
        UserVo userVo = personUserMapper.selectUserVoById(userId);
        UserAndDepartmentVo userAndDepartmentVo = new UserAndDepartmentVo();
        if (ObjectUtil.isNotEmpty(userVo)) {
            if (ObjectUtil.isNotNull(userVo.getBirth())){
                userAndDepartmentVo.setBirth(userVo.getBirth().getTime());
            }
            userAndDepartmentVo.setEmail(userVo.getEmail());
            userAndDepartmentVo.setMobile(userVo.getMobile());
            userAndDepartmentVo.setUserId(userVo.getUserId());
            userAndDepartmentVo.setUserName(userVo.getUserName());
            userAndDepartmentVo.setWork_status(userVo.getWork_status());
            userAndDepartmentVo.setSex(userVo.getSex());
            userAndDepartmentVo.setDepartmentName(userVo.getDepartmentName());
            userAndDepartmentVo.setPostName(userVo.getPostName());
            userAndDepartmentVo.setRoleName(userVo.getRoleName());
            String departmentNameAndPostName = userAndDepartmentVo.getDepartmentName()+userAndDepartmentVo.getDepartmentName();
            userAndDepartmentVo.setDepartmentAndPost(departmentNameAndPostName);

        }
        return userAndDepartmentVo;
    }

    @Override
    @Cacheable(value = "DepartmentUserTree")
    public List<DUserVo> selectByDepartmentId(Integer departmentId) {
        return personUserMapper.selectAllByDepartmentId(departmentId);
    }

    @Override
    @CacheEvict(value = "DepartmentUserTree",allEntries = true)
    @CachePut(value = "user", key = "updateUserDao.userId",unless="#result==null")
    @Transactional
    public UserEnity updateUserById(UpdateUserDao updateUserDao, Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        UserEnity userEnity = null;

        if (updateUserDao.getUserId() == null){
            userEnity = selectByIdMy(userId);
        }else {
            userEnity = selectByIdMy(updateUserDao.getUserId());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getBirth())){
            userEnity.setBirth(updateUserDao.getBirth());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getDepartmentId())){
            userEnity.setDepartmentId(updateUserDao.getDepartmentId());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getMobile())){
            userEnity.setMobile(updateUserDao.getMobile());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getNikeName())){
            userEnity.setNickname(updateUserDao.getNikeName());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getPostId())){
            userEnity.setPostId(updateUserDao.getPostId());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getRoleId())){
            userEnity.setRoleId(updateUserDao.getRoleId());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getSex())){
            userEnity.setSex(updateUserDao.getSex());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getUserName())){
            userEnity.setUserName(updateUserDao.getUserName());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getWorkStatus())){
            userEnity.setWorkStatus(updateUserDao.getWorkStatus());
        }
        if (ObjectUtil.isNotNull(updateUserDao.getUserPassword())){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String passwordEncoder =  bCryptPasswordEncoder.encode(updateUserDao.getUserPassword());
            userEnity.setUserPassword(passwordEncoder);
        }
        userEnity.setUpdateTime(new Date());
        userEnity.setUpdateId(userId);
        if (update(userEnity,new LambdaUpdateWrapper<UserEnity>().eq(UserEnity::getUserId,userEnity.getUserId()))){
            return userEnity;
        }
        return null;
    }

    @Override
    public List<UserAndDepartmentVo> selectAllPeople() {
        List<UserVo> userVos = personUserMapper.selectAllUser();
        List<UserAndDepartmentVo> userAndDepartmentVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userVos)) {
            // 进行拆解封装 转换成DepartmentTreeVo
            userAndDepartmentVos = userVos.stream().map(userVo -> {
                UserAndDepartmentVo userAndDepartmentVo = new UserAndDepartmentVo();
                userAndDepartmentVo.setEmail(userVo.getEmail());
                userAndDepartmentVo.setMobile(userVo.getMobile());
                userAndDepartmentVo.setUserId(userVo.getUserId());
                userAndDepartmentVo.setUserName(userVo.getUserName());
                userAndDepartmentVo.setWork_status(userVo.getWork_status());
                String departmentName = departmentService.getDepartmentName(userVo.getDepartmentId());
                String postName = postMapper.selectPostName(userVo.getPostId());
                String departmentNameAndPostName = departmentName+postName;
                userAndDepartmentVo.setDepartmentAndPost(departmentNameAndPostName);
                return userAndDepartmentVo;
            }).collect(Collectors.toList());
        }
        return userAndDepartmentVos;
    }
}
