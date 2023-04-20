package top.easyblog.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import top.easyblog.common.response.EasyResultCode;

/**
 * @author: frank.huang
 * @date: 2021-11-01 17:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private EasyResultCode code;

    public BusinessException(@NonNull EasyResultCode code) {
        this.code = code;
    }

    public BusinessException(@NonNull EasyResultCode code, String message){
        super(message);
        this.code=code;
    }

    public BusinessException(@NonNull EasyResultCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }


    public String getCode(){
        return this.code.getCode();
    }

}
