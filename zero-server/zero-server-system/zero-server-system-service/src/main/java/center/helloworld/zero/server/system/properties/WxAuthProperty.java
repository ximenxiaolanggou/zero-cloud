package center.helloworld.zero.server.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "zero.weixin.auth")
public class WxAuthProperty {

    /**
     * appid
     */
    private String appid;

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 获取微信认证地址
     * @return
     */
    public String getAuthUrl(String code) {
        return  "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
    }

    /**
     * 获取微信用户信息地址
     * @param access_token
     * @return
     */
    public String getUserInfoUrl(String access_token) {
        return  "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid=" + appid;
    }
}
