package top.easyblog.core;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogRequest;
import top.easyblog.common.request.loginlog.UpdateSignInLogRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicLoginLogService;
import top.easyblog.dao.auto.model.LoginLog;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:34
 */
@Service
public class UserLoginLogService {

    @Autowired
    private AtomicLoginLogService atomicLoginLogService;

    @Autowired
    private BeanMapper beanMapper;

    public LoginLogBean createSignInLog(CreateLoginLogRequest request) {
        LoginLog loginLog = beanMapper.convertLoginLogCreateReq2Account(request);
        loginLog = atomicLoginLogService.insertLoginLogByRequest(loginLog);
        return beanMapper.convertLoginLog2LoginLogBean(loginLog);
    }


    public LoginLogBean queryLoginLogDetails(QueryLoginLogRequest request) {
        return Optional.ofNullable(atomicLoginLogService.querySignLogByRequest(request))
                .map(item -> beanMapper.convertLoginLog2LoginLogBean(item)).orElse(null);
    }


    public PageResponse<LoginLogBean> queryLoginLogList(QueryLoginLogListRequest request) {
        long count = atomicLoginLogService.countByRequest(request);
        if (count == 0) {
            return PageResponse.<LoginLogBean>builder().limit(request.getLimit()).offset(request.getOffset())
                    .total(NumberUtils.LONG_ZERO).data(Collections.emptyList()).build();
        }

        List<LoginLogBean> loginLogBeans = atomicLoginLogService.querySignInLogListByRequest(request).stream().map(loginLog -> {
            return beanMapper.convertLoginLog2LoginLogBean(loginLog);
        }).collect(Collectors.toList());
        return PageResponse.<LoginLogBean>builder().limit(request.getLimit()).offset(request.getOffset())
                .total(count).data(loginLogBeans).build();
    }


    public void updateSignLog(String code, UpdateSignInLogRequest request) {
        LoginLog loginLog = atomicLoginLogService.querySignLogByRequest(QueryLoginLogRequest.builder().code(code).build());
        if(Objects.nonNull(loginLog)){
            LoginLog updateReq2LoginLog = beanMapper.convertLoginLogUpdateReq2LoginLog(loginLog.getId(), request);
            atomicLoginLogService.updateSignInLogByPrimarySelective(updateReq2LoginLog);
            return;
        }

        throw new BusinessException(EasyResultCode.LOGIN_LOG_NOT_FOUND);
    }

}
