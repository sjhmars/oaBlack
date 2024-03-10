package com.kunkun.oaBlack.module.personnelManagement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kunkun.oaBlack.module.personnelManagement.enitly.PostEnity;
import org.springframework.security.core.Authentication;

public interface PostService extends IService<PostEnity> {
    PostEnity addPost(String postName, Authentication authentication);
}
