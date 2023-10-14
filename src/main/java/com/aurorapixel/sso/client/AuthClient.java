package com.aurorapixel.sso.client;

import com.aurorapixel.sso.entity.vo.AuthResponseAccessTokenVO;
import com.aurorapixel.sso.entity.vo.AuthResponseCheckTokenVO;
import com.aurorapixel.sso.entity.CommonResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * 远程客户端请求
 *
 * @author AuroraPixel
 * @github https://github.com/AuroraPixel
 */
@Component
public class AuthClient {

    /**
     * 客户端获取token地址
     */
    private static final String BASE_URL = "http://127.0.0.1:48080/admin-api/system/oauth2";

    /**
     * 租户id
     */
    public static final Long TENANT_ID = 1L;

    /**
     * 客户端密钥
     */
    private static final String CLIENT_ID = "yudao-sso-demo-by-code";

    /**
     * 客户端密钥
     */
    private static final String CLIENT_SECRET = "test";

    private final RestTemplate restTemplate = new RestTemplate();


    /**
     * 获取token
     * @param code 授权码
     * @param redirectURL 重定向地址
     * @return 令牌
     */
    public CommonResult<AuthResponseAccessTokenVO> postAccessToken (String code,String redirectURL){
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("tenant-id", TENANT_ID.toString());
        addClientHeader(headers);
        //设置请求参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectURL);
        // 2. 执行请求
        ResponseEntity<CommonResult<AuthResponseAccessTokenVO>> exchange = restTemplate.exchange(
                BASE_URL + "/token",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<CommonResult<AuthResponseAccessTokenVO>>() {}); // 解决 CommonResult 的泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }

    /**
     * 校验token
     *
     * @param token token令牌
     * @return 令牌的基本信息
     */
    public CommonResult<AuthResponseCheckTokenVO> checkToken(String token){
        // 1.1 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("tenant-id", TENANT_ID.toString());
        addClientHeader(headers);
        // 1.2 构建请求参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("token", token);

        // 2. 执行请求
        ResponseEntity<CommonResult<AuthResponseCheckTokenVO>> exchange = restTemplate.exchange(
                BASE_URL + "/check-token",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<CommonResult<AuthResponseCheckTokenVO>>() {}); // 解决 CommonResult 的泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }

    /**
     * 设置客户端信息
     * @param headers 请求头
     */
    private static void addClientHeader(HttpHeaders headers) {
        // client 拼接，需要 BASE64 编码
        String client = CLIENT_ID + ":" + CLIENT_SECRET;
        client = Base64Utils.encodeToString(client.getBytes(StandardCharsets.UTF_8));
        headers.add("Authorization", "Basic " + client);
    }
}
