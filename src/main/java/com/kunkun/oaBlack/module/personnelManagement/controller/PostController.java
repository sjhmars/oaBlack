package com.kunkun.oaBlack.module.personnelManagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.module.personnelManagement.enitly.PostEnity;
import com.kunkun.oaBlack.module.personnelManagement.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@Api(tags = "岗位模块")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation("新增岗位")
    @PostMapping("/addPost")
    public ResultUtil addPost(@RequestBody String postName){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        PostEnity postEnity = postService.addPost(postName,authentication);
        if (postEnity!=null){
            return ResultUtil.success(postEnity);
        }
        return ResultUtil.faile("新增岗位失败");
    }

    @ApiOperation("查询岗位列表")
    @GetMapping("/selectPost")
    @Cacheable(value = "post")
    public ResultUtil selectPost(){
        List<PostEnity> postEnities = postService.list();
        return ResultUtil.success(postEnities);
    }

    public ResultUtil deletePost(){
        return null;
    }

    public ResultUtil updatePost(){
        return null;
    }
}
