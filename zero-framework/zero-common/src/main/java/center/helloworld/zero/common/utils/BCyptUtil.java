package center.helloworld.zero.common.utils;

import cn.hutool.crypto.digest.BCrypt;
import org.apache.commons.lang3.StringUtils;

public class BCyptUtil {

    /**
     * 传递参数密码 返回加密后的密码 和 盐
     *
     * @param password
     * @return
     */
    public static String[] getPasswordAndSalt(String password) {
        if (StringUtils.isBlank(password)) {
            return new String[]{null, null};
        }
        String gensalt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(password, gensalt);
        return new String[]{hashpw, gensalt};
    }
}
