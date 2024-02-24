package com.kunkun.oaBlack.common.handler;

import com.alibaba.fastjson.JSON;
import com.kunkun.oaBlack.common.util.CodeUtil;
import com.kunkun.oaBlack.common.util.ResultUtil;
import com.kunkun.oaBlack.common.util.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultUtil result = ResultUtil.faile(CodeUtil.NO_PERMISSION.getResultCode(),CodeUtil.NO_PERMISSION.getResultMessage());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
