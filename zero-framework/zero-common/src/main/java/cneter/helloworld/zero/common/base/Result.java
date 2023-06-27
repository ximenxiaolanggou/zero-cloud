package cneter.helloworld.zero.common.base;

import cneter.helloworld.zero.common.code.ResCode;
import cneter.helloworld.zero.common.exception.ApiException;
import lombok.Data;

/**
 * @author zhishun.cai
 * @date 2021/4/1 16:38
 */

@Data
public class Result {

    /**
     * 成功|失败
     */
    private Boolean flag;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;


    public Result() {

    }

    public Result(ApiException e) {
        this.flag = false;
        this.code = e.getCode();
        this.data = e.getData();
        this.msg = e.getErrMsg();
    }

    public Result(ResCode resCode) {
        this.flag = resCode.isFlag();
        this.code = resCode.getCode();
        this.msg = resCode.getMessage();
    }

    public Result(ResCode resCode, Object obj) {
        this.flag = resCode.isFlag();
        this.code = resCode.getCode();
        this.msg = resCode.getMessage();
        this.data = obj;
    }


    public static Result ok(Object data) {
        Result result = new Result();
        result.setFlag(true);
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static Result fail(Object data) {
        Result result = new Result();
        result.setFlag(false);
        result.setCode(0);
        result.setMsg("操作失败");
        result.setData(data);
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        result.setFlag(true);
        result.setCode(1);
        result.setMsg("操作成功");
        return result;
    }

    public static Result fail() {
        Result result = new Result();
        result.setFlag(false);
        result.setCode(0);
        result.setMsg("操作失败");
        return result;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.msg = message;
    }

    public Result(Boolean flag, String message, Object data) {
        this.flag = flag;
        this.msg = message;
        this.data = data;
    }
}
