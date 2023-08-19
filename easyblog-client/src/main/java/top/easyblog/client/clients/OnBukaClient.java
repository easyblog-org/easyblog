package top.easyblog.client.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.easyblog.client.config.OnBukaFeignConfig;
import top.easyblog.client.dto.request.BukaSmsSendRequest;
import top.easyblog.client.dto.response.dto.BukaSmsSendDTO;
import top.easyblog.client.internal.Verify;

/**
 * @author: frank.huang
 * @date: 2023-08-19 13:37
 */
@FeignClient(name = "buka", url = "${sms.onbuka.host}", configuration = OnBukaFeignConfig.class)
public interface OnBukaClient extends Verify {


    /**
     * 发送短信
     * https://www.onbuka.cn/sms-api3/
     *
     * @param request
     * @return
     */
    @PostMapping("/v3/sendSms")
    BukaSmsSendDTO sendSms(@RequestBody BukaSmsSendRequest request);

}
