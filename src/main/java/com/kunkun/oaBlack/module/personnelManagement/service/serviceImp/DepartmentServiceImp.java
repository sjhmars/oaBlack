package com.kunkun.oaBlack.module.personnelManagement.service.serviceImp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.dao.PagesDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.UserEnity;
import com.kunkun.oaBlack.module.personnelManagement.dao.AddDepartmentDao;
import com.kunkun.oaBlack.module.personnelManagement.enitly.DepartmentEnitly;
import com.kunkun.oaBlack.module.personnelManagement.mapper.DepartmentMapper;
import com.kunkun.oaBlack.module.personnelManagement.service.DepartmentService;
import com.kunkun.oaBlack.module.personnelManagement.service.PersonUserService;
import com.kunkun.oaBlack.module.personnelManagement.vo.DUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeUserVo;
import com.kunkun.oaBlack.module.personnelManagement.vo.DepartmentTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImp extends ServiceImpl<DepartmentMapper, DepartmentEnitly> implements DepartmentService{

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private PersonUserService personUserService;

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    @Transactional
    @CacheEvict(value = "DepartmentUserTree",allEntries=true)
    public ResultUtil addDepartment(AddDepartmentDao departmentDao,Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String tokenValue = details.getTokenValue();
        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(tokenValue);
        Integer userId = (Integer) oAuth2AccessToken.getAdditionalInformation().get("userid");

        DepartmentEnitly departmentEnitly = new DepartmentEnitly();
        departmentEnitly.setCreateTime(new Date());
        departmentEnitly.setDepartmentName(departmentDao.getDepartmentName());
        departmentEnitly.setSuperiorDepartment(departmentDao.getSuperiorDepartment());
        departmentEnitly.setUserId(departmentDao.getUserId());
        departmentEnitly.setIsDelete(0);
        if (userId!=null){
            UserEnity userEnity =  personUserService.selectByIdMy(userId);
            if (ObjectUtil.isNotNull(userEnity)) {
                departmentEnitly.setHeadUserName(userEnity.getUserName());
            }
        }
        DepartmentEnitly departmentEnitlyOld = departmentMapper.selectById(departmentDao.getSuperiorDepartment());
        if (departmentEnitlyOld == null){
            return ResultUtil.faile("没有这个父级部门");
        }
        departmentMapper.insert(departmentEnitly);
        if (updateUserResponsibleDepartmentIds(departmentDao.getUserId(),departmentEnitly.getDepartmentId(),userId)!=null){
            return ResultUtil.success("新增部门成功",departmentEnitly);
        }
        return ResultUtil.faile("更新失败");
    }

    @Override
    public List<DepartmentTreeVo> getDepartmentTree(Authentication authentication) {
        List<DepartmentEnitly> departmentEnitlies = departmentMapper.selectAll();

        List<DepartmentTreeVo> departmentTreeVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(departmentEnitlies)) {
            // 进行拆解封装 转换成DepartmentTreeVo
                departmentTreeVoList = departmentEnitlies.stream().map(departmentEnitly1 -> {
                DepartmentTreeVo departmentTreeVo = new DepartmentTreeVo();
                departmentTreeVo.setDepartmentId(departmentEnitly1.getDepartmentId());
                departmentTreeVo.setDepartmentName(departmentEnitly1.getDepartmentName());
                departmentTreeVo.setSuperiorDepartment(departmentEnitly1.getSuperiorDepartment());
                departmentTreeVo.setUserId(departmentEnitly1.getUserId());
                return departmentTreeVo;
            }).collect(Collectors.toList());
        }

        List<DepartmentTreeVo> departmentTreeVoList1 = new ArrayList<>();
        for (DepartmentTreeVo departmentTreeVo: departmentTreeVoList) {
            if (departmentTreeVo.getSuperiorDepartment() == null){
                recursionFn(departmentTreeVoList,departmentTreeVo);
                departmentTreeVoList1.add(departmentTreeVo);
            }
        }
        if (departmentTreeVoList1.isEmpty()){
            departmentTreeVoList1 = departmentTreeVoList;
        }
        return departmentTreeVoList1;
    }

    @Override
    public String getDepartmentName(Integer DepartmentId) {
        DepartmentEnitly departmentEnitly = departmentMapper.selectById(DepartmentId);
        List<String> DepartmentName = new ArrayList<>();
        DepartmentName.add(departmentEnitly.getDepartmentName());
        while (departmentEnitly.getSuperiorDepartment()!=null){
            departmentEnitly = departmentMapper.selectById(departmentEnitly.getSuperiorDepartment());
            DepartmentName.add(departmentEnitly.getDepartmentName());
        }
        Collections.reverse(DepartmentName);
        StringBuilder name = new StringBuilder();
        for (String departName: DepartmentName){
            name.append(departName).append("/");
        }
        return name.toString();
    }

    @Override
    public String getOneName(Integer DepartmentId) {

        return null;
    }

    @Override
    public IPage<DepartmentTreeUserVo> getDepartmentTreeUserVoTree(PagesDao pagesDao) {
        if (pagesDao.getPageSize() == null){
            pagesDao.setPageSize(10);
        }
        Page<DepartmentEnitly> departmentEnitlyPage = new Page<>(pagesDao.getPageNumber(),pagesDao.getPageSize());
        IPage<DepartmentEnitly> departmentEnitliePages = departmentMapper.selectPageAll(departmentEnitlyPage);
        List<DepartmentEnitly> departmentEnitlies = departmentEnitliePages.getRecords();
        List<DepartmentTreeUserVo> departmentTreeUserVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(departmentEnitlies)) {
            // 进行拆解封装 转换成DepartmentTreeVo
            departmentTreeUserVoList = departmentEnitlies.stream().map(departmentEnitly -> {
                DepartmentTreeUserVo departmentTreeUserVo = new DepartmentTreeUserVo();
                departmentTreeUserVo.setDepartmentId(departmentEnitly.getDepartmentId());
                departmentTreeUserVo.setDepartmentName(departmentEnitly.getDepartmentName());
                departmentTreeUserVo.setSuperiorDepartment(departmentEnitly.getSuperiorDepartment());
                departmentTreeUserVo.setUserId(departmentEnitly.getUserId());
                departmentTreeUserVo.setHeadUserName(departmentEnitly.getHeadUserName());
//                UserEnity userEnity = personUserService.selectByIdMy(departmentEnitly);
                List<DUserVo> dUserVoList = personUserService.selectByDepartmentId(departmentEnitly.getDepartmentId());
                departmentTreeUserVo.setDUserVoList(dUserVoList);
                return departmentTreeUserVo;
            }).collect(Collectors.toList());
        }
        List<DepartmentTreeUserVo> departmentTreeUserVos = new ArrayList<>();
        for (DepartmentTreeUserVo departmentTreeUserVo: departmentTreeUserVoList) {
            if (departmentTreeUserVo.getSuperiorDepartment() == null){
                UserTreeRecursionFn(departmentTreeUserVoList,departmentTreeUserVo);
                departmentTreeUserVos.add(departmentTreeUserVo);
            }
        }
        if (departmentTreeUserVos.isEmpty()){
            departmentTreeUserVos = departmentTreeUserVoList;
        }
        IPage<DepartmentTreeUserVo> departmentTreeUserVoIPage = new Page<>();
        departmentTreeUserVoIPage.setCurrent(departmentEnitliePages.getCurrent())
                .setRecords(departmentTreeUserVos)
                .setPages(departmentEnitliePages.getPages())
                .setSize(departmentEnitliePages.getSize())
                .setTotal(departmentEnitliePages.getTotal());
        return departmentTreeUserVoIPage;
    }

    @Override
    public DepartmentEnitly updataHeadUserId(Integer UserId,Authentication authentication) {
        return null;
    }

    private void UserTreeRecursionFn(List<DepartmentTreeUserVo> list, DepartmentTreeUserVo departmentTreeUserVo) {
        // 得到子节点列表
        List<DepartmentTreeUserVo> childList = getUserTreeChildList(list, departmentTreeUserVo);
        departmentTreeUserVo.setChildren(childList);
        for (DepartmentTreeUserVo tChild : childList) {
            // 如果子节点有下一级节点，得到下一级的节点列表
            if (UserTreeHasChild(list, tChild)) {
                UserTreeRecursionFn(list, tChild);
            }
        }
    }

    private List<DepartmentTreeUserVo> getUserTreeChildList(List<DepartmentTreeUserVo> list, DepartmentTreeUserVo departmentTreeUserVo) {
        List<DepartmentTreeUserVo> deptList = new ArrayList<>();
        for(DepartmentTreeUserVo d:list){
            // 遍历除顶级节点外的其他节点，并把除当前节点外的其他节点加入列表
            if (d.getSuperiorDepartment() != null && d.getSuperiorDepartment().equals(departmentTreeUserVo.getDepartmentId())) {
                deptList.add(d);
            }
        }
        return deptList;
    }

    private boolean UserTreeHasChild(List<DepartmentTreeUserVo> list, DepartmentTreeUserVo dept) {
        return getUserTreeChildList(list, dept).size() > 0;
    }


    private void recursionFn(List<DepartmentTreeVo> list, DepartmentTreeVo DepartmentTreeVo) {
        // 得到子节点列表
        List<DepartmentTreeVo> childList = getChildList(list, DepartmentTreeVo);
        DepartmentTreeVo.setChildren(childList);
        for (DepartmentTreeVo tChild : childList) {
            // 如果子节点有下一级节点，得到下一级的节点列表
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    private List<DepartmentTreeVo> getChildList(List<DepartmentTreeVo> list, DepartmentTreeVo departmentTreeVo) {
        List<DepartmentTreeVo> deptList = new ArrayList<>();
        for(DepartmentTreeVo d:list){
            // 遍历除顶级节点外的其他节点，并把除当前节点外的其他节点加入列表
            if (d.getSuperiorDepartment() != null && d.getSuperiorDepartment().equals(departmentTreeVo.getDepartmentId())) {
                deptList.add(d);
            }
        }
        return deptList;
    }

    private boolean hasChild(List<DepartmentTreeVo> list, DepartmentTreeVo dept) {
        return getChildList(list, dept).size() > 0;
    }



    @CachePut(value = "user", key = "#userId",unless="#result==null")
    public UserEnity updateUserResponsibleDepartmentIds(Integer userId,Integer departmentId,Integer change_userId){
        UserEnity userEnityold = personUserService.selectByIdMy(userId);
        String departmentIds = userEnityold.getDepartmentIds();
        departmentIds = departmentIds+","+departmentId;
        userEnityold.setDepartmentIds(departmentIds);
        userEnityold.setUpdateId(change_userId);
        userEnityold.setUpdateTime(new Date());
        if (personUserService.update(userEnityold,new LambdaUpdateWrapper<UserEnity>().eq(UserEnity::getUserId,userEnityold.getUserId()))){
            return userEnityold;
        }
        return null;
    }
}
