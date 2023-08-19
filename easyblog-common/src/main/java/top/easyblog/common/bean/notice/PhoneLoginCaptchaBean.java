package top.easyblog.common.bean.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-08-08 21:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLoginCaptchaBean {
    private String phone;
    private String captchaCode;
}
