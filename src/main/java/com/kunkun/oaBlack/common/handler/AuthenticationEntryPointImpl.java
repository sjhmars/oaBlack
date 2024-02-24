package com.kunkun.oaBlack.common.handler;

import com.alibaba.fastjson.JSON;
import com.kunkun.oaBlack.common.util.CodeUtil;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.common.util.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultUtil result = ResultUtil.faile(CodeUtil.LOING_FAILE.getResultCode(),CodeUtil.LOING_FAILE.getResultMessage());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
