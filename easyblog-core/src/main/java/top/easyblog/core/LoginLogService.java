package top.easyblog.core;

import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.QuerySection;
import top.easyblog.common.enums.Status;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.loginlog.CreateLoginLogRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogRequest;
import top.easyblog.common.request.loginlog.UpdateLoginLogRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicLoginLogService;
import top.easyblog.dao.auto.model.LoginLog;
import top.easyblog.service.section.IUserSectionInquireService;
import top.easyblog.support.context.UserSectionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:34
 */
@Service
public class LoginLogService implements IUserSectionInquireService {

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

        List<LoginLogBean> loginLogBeans = atomicLoginLogService.querySignInLogListByRequest(request).stream()
                .map(loginLog -> {
                    return beanMapper.convertLoginLog2LoginLogBean(loginLog);
                }).collect(Collectors.toList());
        return PageResponse.<LoginLogBean>builder().limit(request.getLimit()).offset(request.getOffset())
                .total(count).data(loginLogBeans).build();
    }

    public void updateSignLog(String code, UpdateLoginLogRequest request) {
        LoginLog loginLog = atomicLoginLogService
                .querySignLogByRequest(QueryLoginLogRequest.builder().code(code).build());
        if (Objects.nonNull(loginLog)) {
            LoginLog updateReq2LoginLog = beanMapper.convertLoginLogUpdateReq2LoginLog(loginLog.getId(), request);
            atomicLoginLogService.updateLoginLogByPrimarySelective(updateReq2LoginLog);
            return;
        }

        throw new BusinessException(EasyResultCode.LOGIN_LOG_NOT_FOUND);
    }

    @Override
    public void execute(String section, UserSectionContext ctx, Map<Long, String> userIdCodesMap,
                        boolean queryWhenSectionEmpty) {
        if (MapUtils.isEmpty(userIdCodesMap)) {
            return;
        }

        List<String> userCodes = new ArrayList<>(userIdCodesMap.values());
        if (StringUtils.containsIgnoreCase(section, QuerySection.QUERY_SIGN_LOG.getName())) {
            QueryLoginLogListRequest request = QueryLoginLogListRequest.builder()
                    .userCodes(userCodes).status(Status.ENABLE.getCode().intValue()).offset(NumberUtils.INTEGER_ZERO)
                    .limit(Constants.QUERY_LIMIT_MAX_THOUSAND).build();
            PageResponse<LoginLogBean> loginLogBeanPageResponse = queryLoginLogList(request);
            List<LoginLogBean> loginLogBeans = loginLogBeanPageResponse.getData();
            Map<String, List<LoginLogBean>> loginLogBeanMap = loginLogBeans.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(LoginLogBean::getUserCode));
            ctx.setSignInLogsMap(loginLogBeanMap);
        }
    }

}
