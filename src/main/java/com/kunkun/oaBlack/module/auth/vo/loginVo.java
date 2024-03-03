package com.kunkun.oaBlack.module.auth.vo;

import lombok.Data;

import java.util.Date;

@Data
public class loginVo {
    
    private String userName;

    private String access_token;

    private String roleName;

    private String nickname;

    private Long lastLoginTime;

    private String lastLoginAddress;
}
