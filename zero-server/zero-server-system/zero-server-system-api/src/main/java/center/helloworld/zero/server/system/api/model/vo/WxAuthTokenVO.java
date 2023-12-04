package center.helloworld.zero.server.system.api.model.vo;

import lombok.Data;

/**
 * 认证信息
 */
@Data
public class WxAuthTokenVO {
    /**
     * 访问令牌
     */
    private String access_token;

    /**
     * 过期时间
     */
    private Long expires_in;

    /**
     * 刷新令牌
     */
    private String refresh_token;

    /**
     * open id
     */
    private String openid;

    /**
     * 服务范围
     */
    private String scope;

    /**
     * 用户唯一标识
     */
    private String unionid;
}
