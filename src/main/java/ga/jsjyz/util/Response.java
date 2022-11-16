package ga.jsjyz.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private int code;
    private String message;
    private Object data;

    public Response(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
    public Response ok(Object data) {
        this.code = 10000;
        this.message = "成功";
        this.data = data;
        return this;
    }
}
