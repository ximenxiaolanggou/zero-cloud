package cneter.helloworld.zero.common.code;

/**
 * @author zhishun.cai
 * @date 2021/4/1 16:43
 * @note
 */

public enum ResCode {

    ERROR(false, 0, "操作失败"),
    SUCCESS(true, 1, "操作成功"),
    SYSTEM_ERROR(false, 2, "系统异常"),
    ERROR_REQUEST_PARAMS(false, 3, "请求参数错误"),
    // 权限
    ERROR_PERMISSION_EXIST(false,4,"权限已存在"),

    ERROR_ROLE_EXIST(false,5,"角色名称已存在"),

    ERROR_EMIAL_EXIST(false,6,"邮箱号已存在"),
    // -- 手机 --
    ERROR_MOBILE_EXIST(false,7,"手机号已存在"),

    ERROR_USERNAME_OR_PWD(false, 8, "用户名或密码错误"),

    ;

    private boolean flag;

    private Integer code;

    private String message;

    ResCode() {
    }

    ResCode(boolean flag, Integer code, String message) {
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
