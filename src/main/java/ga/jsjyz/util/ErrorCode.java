package ga.jsjyz.util;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum ErrorCode {
    ERROR_PARAMETER(10001,"参数错误"),
    FAILED(10101,"Failed"),
    ERROR_EMPTY(10002,"对象为空"),

    ALTER_FAILED(10003,"修改失败"),

    NO_LOGIN(10004,"未登录"),
    NO_PERMISSIONS(10005,"无权限")
    ;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

}
