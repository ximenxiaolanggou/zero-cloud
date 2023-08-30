package center.helloworld.zero.server.system.api.model.vo;

import lombok.Data;

/**
 * 登录VO对象
 */
@Data
public class LoginVO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
