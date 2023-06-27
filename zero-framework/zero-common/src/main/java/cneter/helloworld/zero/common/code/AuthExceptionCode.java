package cneter.helloworld.zero.common.code;

/**
 * <p>
 * 服务器认证异常翻译 code 范围 [10100 ~ 10200)
 * </p>
 *
 * @author zhishun.cai
 * @date 2023/6/27
 */
public enum AuthExceptionCode {

    ERROR_INVALID_TOKEN(false, 10100, "无效token"),
    ERROR_NO_PERMISSION(false, 10101, "没有权限访问该资源"),
    ;

    private boolean flag;

    private Integer code;

    private String message;

    AuthExceptionCode() {
    }

    AuthExceptionCode(boolean flag, Integer code, String message) {
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
