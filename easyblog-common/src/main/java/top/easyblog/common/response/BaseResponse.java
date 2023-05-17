package top.easyblog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import top.easyblog.common.constant.Constants;

import java.io.Serializable;

/**
 * System uniform response object
 *
 * @author: frank.huang
 * @date: 2021-11-01 08:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

    private T data;
    private String requestId;
    private String message;
    private String code;
    private Boolean success;
    private Long serverTime;
    private String path;

    public BaseResponse(T data, String message, String code) {
        this.data = data;
        this.path = MDC.get(Constants.REQUEST_URL);
        this.message = message;
        this.code = code;
        this.success = isSuccess();
        this.requestId = MDC.get(Constants.REQUEST_ID);
        this.serverTime = System.currentTimeMillis();
    }

    public BaseResponse(T data, String path, String message, String code) {
        this.data = data;
        this.path = path;
        this.message = message;
        this.code = code;
        this.success = isSuccess();
        this.requestId = MDC.get(Constants.REQUEST_ID);
        this.serverTime = System.currentTimeMillis();

    }

    public boolean isSuccess() {
        return EasyResultCode.SUCCESS.getCode().equalsIgnoreCase(this.code);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data, "ok", EasyResultCode.SUCCESS.getCode());
    }

    public static <T> BaseResponse<T> fail() {
        return new BaseResponse<>(null, "fail", EasyResultCode.INTERNAL_ERROR.getCode());
    }

    public static <T> BaseResponse<T> fail(String code, String message) {
        return new BaseResponse<>(null, message, code);
    }

}
