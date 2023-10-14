package com.aurorapixel.sso.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseAccessTokenVO {
    /**
     * 访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 令牌类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * 过期时间；单位：秒
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    /**
     * 授权范围；如果多个授权范围，使用空格分隔
     */
    private String scope;
}
