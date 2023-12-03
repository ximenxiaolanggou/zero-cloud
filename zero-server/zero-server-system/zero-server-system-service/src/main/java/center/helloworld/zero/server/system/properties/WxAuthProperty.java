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
}
