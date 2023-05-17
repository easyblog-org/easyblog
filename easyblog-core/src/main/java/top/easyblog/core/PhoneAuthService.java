package top.easyblog.core;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.phoneauth.CreatePhoneAuthRequest;
import top.easyblog.common.request.phoneauth.QueryPhoneAuthRequest;
import top.easyblog.common.request.phoneauth.UpdatePhoneAuthRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicPhoneAuthService;
import top.easyblog.dao.auto.model.PhoneAuth;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-10 23:10
 */
@Service
public class PhoneAuthService {

    @Autowired
    private AtomicPhoneAuthService atomicPhoneAuthService;

    @Autowired
    private BeanMapper beanMapper;


    public Long createPhoneAuth(CreatePhoneAuthRequest request) {
        PhoneAuth phoneAuth = queryPhoneAuthDetails(QueryPhoneAuthRequest.builder().phoneAreaCode(request.getPhoneAreaCode()).phone(request.getPhone()).build());
        if (Objects.nonNull(phoneAuth)) {
            throw new BusinessException(EasyResultCode.PHONE_ACCOUNT_ALREADY_EXISTS);
        }

        phoneAuth = beanMapper.convertPhoneAuthCreateReq2PhoneAuth(request);
        return atomicPhoneAuthService.insertByRequestSelective(phoneAuth);
    }



    public PhoneAuth queryPhoneAuthDetails(QueryPhoneAuthRequest request) {
        return atomicPhoneAuthService.queryPhoneAuthByRequest(request);
    }


    public void updatePhoneAuth(UpdatePhoneAuthRequest request) {
        PhoneAuth phoneAuth = new PhoneAuth();
        BeanUtils.copyProperties(request, phoneAuth);
        atomicPhoneAuthService.updatePhoneAuthByRequest(phoneAuth);
    }

}
