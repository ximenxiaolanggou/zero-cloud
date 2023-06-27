package cneter.helloworld.zero.common.code;

/**
 * <p>
 * 异常翻译code 范围 [10000 ~ 10100)
 * </p>
 *
 * @author zhishun.cai
 * @since 2022/9/22 10:36
 */
public enum ExceptionTranslatorCode {
    ERROR_AUTH(false, 10000, "认证失败"),
    ERROR_UNSUPPROT_GRANT_TYPE(false, 10001, "不支持该认证类型"),
    ERROR_INVALID_REFRESH_TOKEN(false, 10002, "refresh token无效"),
    ERROR_ACCOUNT_LOCK(false, 10003, "用户已被锁定，请联系管理员"),
    ERROR_USERNAME_OR_PASSWORD(false, 10004, "用户名或密码错误"),
    ;

    private boolean flag;

    private Integer code;

    private String message;

    ExceptionTranslatorCode() {
    }

    ExceptionTranslatorCode(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
