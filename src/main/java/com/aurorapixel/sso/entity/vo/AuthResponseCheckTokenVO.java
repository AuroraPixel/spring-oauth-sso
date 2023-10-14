package com.aurorapixel.sso.entity.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseCheckTokenVO {
    /**
     * 用户编号
     */
    @JsonProperty("user_id")
    private Long userId;
    /**
     * 用户类型
     */
    @JsonProperty("user_type")
    private Integer userType;
    /**
     * 租户编号
     */
    @JsonProperty("tenant_id")
    private Long tenantId;

    /**
     * 客户端编号
     */
    @JsonProperty("client_id")
    private String clientId;
    /**
     * 授权范围
     */
    private List<String> scopes;

    /**
     * 访问令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 过期时间
     *
     * 时间戳 / 1000，即单位：秒
     */
    private Long exp;
}
