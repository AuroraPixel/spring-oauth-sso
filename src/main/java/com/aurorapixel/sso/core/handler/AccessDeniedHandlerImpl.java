package com.aurorapixel.sso.core.handler;

import cn.hutool.http.HttpStatus;
import com.aurorapixel.sso.core.utils.ServletUtil;
import com.aurorapixel.sso.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 * @author AuroraPixel
 * @github https://github.com/AuroraPixel
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //log.warn("[commence][访问 URL({})时，用户({})无权限]", request.getRequestURI(),SecurityUtil.getLoginUser);
        //返回403
        CommonResult<Object> result = new CommonResult<>();
        result.setCode(HttpStatus.HTTP_FORBIDDEN);
        result.setMsg("无权限");
        ServletUtil.writeJSON(response,result);
    }
}
