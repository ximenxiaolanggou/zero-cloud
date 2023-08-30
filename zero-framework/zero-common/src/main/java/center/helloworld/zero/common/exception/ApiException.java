package center.helloworld.zero.common.exception;

import center.helloworld.zero.common.code.ResCode;
import lombok.Data;

/**
 * @author zhishun.cai
 * @date 2023/6/27
 *
 * 自定义全局异常
 */

@Data
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -5400704137609742487L;
    private Integer code;
    private String errMsg;
    private Object data;

    public ApiException(Integer code, String errMsg) {
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }

    public ApiException(Integer code, String errMsg, Object data) {
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
    }

    public ApiException(ResCode resCode) {
        super(resCode.getMessage());
        this.code = resCode.getCode();
        this.errMsg = resCode.getMessage();
    }

    public ApiException(ResCode resCode, String message) {
        super(resCode.getMessage());
        this.code = resCode.getCode();
        this.errMsg = message;
    }

    public ApiException(){

    }

    public ApiException(String errMsg){
        super(errMsg);
        this.code = 0;
        this.errMsg = errMsg;
    }

}
