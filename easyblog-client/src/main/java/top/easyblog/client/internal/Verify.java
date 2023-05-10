package top.easyblog.client.internal;



import top.easyblog.client.model.response.Response;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author: frank.huang
 * @date: 2021-11-14 21:29
 */
public interface Verify {

    /**
     * 使用request方法进行feign调用
     *
     * @param request
     * @param <T>
     * @return
     */
    default <T> T request(Supplier<Response<T>> request) {
        Response<T> response = request.get();
        this.throwIfFail(response, EasyResultCode.REMOTE_INVOKE_FAIL);
        return response.data();
    }

    default <T> void throwIfFail(Response<T> response, EasyResultCode resultCode) {
        if (Objects.isNull(response) || !response.isSuccess()) {
            throw new BusinessException(resultCode, response.message());
        }
    }

}
