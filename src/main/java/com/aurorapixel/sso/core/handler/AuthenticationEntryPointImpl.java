package com.aurorapixel.sso.core.handler;

import cn.hutool.http.HttpStatus;
import com.aurorapixel.sso.core.utils.ServletUtil;
import com.aurorapixel.sso.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权认证入口实现: 访问需要登录URL时，如果未登录，返回JSON格式的 401+“账户未登录”
 *
 * @author AuroraPixel
 * @github https://github.com/AuroraPixel
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("[commence][访问 URL({})时，没有登录]", request.getRequestURI());
        //返回401
        CommonResult<Object> result = new CommonResult<>();
        result.setCode(HttpStatus.HTTP_UNAUTHORIZED);
        result.setMsg("账户未登录");
        ServletUtil.writeJSON(response,result);

    }
}
