package com.kunkun.oaBlack.module.auth.vo;

import lombok.Data;

@Data
public class loginVo {
    
    private String userName;

    private String access_token;

    private String roleName;

    private String nickname;
}
