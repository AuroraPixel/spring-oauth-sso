package com.aurorapixel.sso.core.fiter;

import com.aurorapixel.sso.client.AuthClient;
import com.aurorapixel.sso.core.utils.SecurityUtils;
import com.aurorapixel.sso.entity.CommonResult;
import com.aurorapixel.sso.entity.dto.LoginUser;
import com.aurorapixel.sso.entity.vo.AuthResponseCheckTokenVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权过滤器 检验访问其它的资源携带的token
 *
 * @author AuroraPixel
 * @github https://github.com/AuroraPixel
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private AuthClient authClient;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 获得访问令牌
        String token = SecurityUtils.obtainAuthorization(request, "Authorization");
        if (StringUtils.hasText(token)) {
            // 2. 基于 token 构建登录用户
            LoginUser loginUser = buildLoginUserByToken(token);
            // 3. 设置当前用户
            if (loginUser != null) {
                SecurityUtils.setLoginUser(loginUser, request);
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String token) {
        try {
            CommonResult<AuthResponseCheckTokenVO> accessTokenResult = authClient.checkToken(token);
            AuthResponseCheckTokenVO accessToken = accessTokenResult.getData();
            if (accessToken == null) {
                return null;
            }
            // 构建登录用户
            return new LoginUser().setId(accessToken.getUserId()).setUserType(accessToken.getUserType())
                    .setTenantId(accessToken.getTenantId()).setScopes(accessToken.getScopes())
                    .setAccessToken(accessToken.getAccessToken());
        } catch (Exception exception) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }
}
