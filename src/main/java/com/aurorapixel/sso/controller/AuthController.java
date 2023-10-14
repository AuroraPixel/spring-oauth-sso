package com.aurorapixel.sso.controller;

import com.aurorapixel.sso.client.AuthClient;
import com.aurorapixel.sso.entity.vo.AuthResponseAccessTokenVO;
import com.aurorapixel.sso.entity.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 认证登录接口
 *
 * @author AuroraPixel
 * @github https://github.com/AuroraPixel
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthClient authClient;

    /**
     * 授权码获取token
     * @param code
     * @param redirectUri
     * @return
     */
    @PostMapping("/login-by-code")
    public CommonResult<AuthResponseAccessTokenVO> loginByCode(@RequestParam("code") String code,
                                                               @RequestParam("redirectUri") String redirectUri
    ) {
        return authClient.postAccessToken(code,redirectUri);
    }
}
