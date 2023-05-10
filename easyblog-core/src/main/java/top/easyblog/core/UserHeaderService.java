package top.easyblog.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.UserHeaderBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.Status;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.common.request.header.QueryUserHeadersRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicUserHeaderService;
import top.easyblog.dao.auto.model.UserHeader;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:19
 */
@Service
public class UserHeaderService {

    @Autowired
    private AtomicUserHeaderService headerImgService;

    @Value("${custom.default-header-image}")
    private String defaultHeaderImg;

    @Autowired
    private BeanMapper beanMapper;

    public void createUserHeader(CreateUserHeaderRequest request) {
        UserHeader UserHeader = headerImgService.queryByRequest(QueryUserHeaderImgRequest.builder()
                .userCode(request.getUserCode()).status(Status.ENABLE.getCode()).build());
        //更新用户之前的头像状态为失效
        Optional.ofNullable(UserHeader).ifPresent(imgBean -> {
            UserHeader.setStatus(Status.DISABLE.getCode());
            headerImgService.updateHeaderImgByRequest(UserHeader);
        });
        if (StringUtils.isBlank(request.getHeaderImgUrl())) {
            //设置默认头像
            request.setHeaderImgUrl(defaultHeaderImg);
        }

        UserHeader userHeader=beanMapper.convertUserHeaderCreateReq2UserHeader(request);
        headerImgService.createUserHeaderImgSelective(userHeader);
    }


    public UserHeaderBean queryUserHeaderDetails(QueryUserHeaderImgRequest request) {
        UserHeader UserHeader = headerImgService.queryByRequest(request);
        return Optional.ofNullable(UserHeader).map(item -> {
            UserHeaderBean userHeaderImgBean = new UserHeaderBean();
            BeanUtils.copyProperties(UserHeader, userHeaderImgBean);
            return userHeaderImgBean;
        }).orElse(null);
    }


    public PageResponse<UserHeaderBean> queryUserHeaderList(QueryUserHeadersRequest request) {
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页，默认查询1000条数据
            request.setOffset(NumberUtils.INTEGER_ZERO);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.QUERY_LIMIT_ONE_THOUSAND : request.getLimit());
            List<UserHeaderBean> userHeaderImgBeans = queryUserHeaderBeans(request);
            return PageResponse.<UserHeaderBean>builder()
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .total((long) userHeaderImgBeans.size())
                    .data(userHeaderImgBeans)
                    .build();
        }


        long count = headerImgService.countByRequest(request);
        if (count == 0) {
            return PageResponse.<UserHeaderBean>builder().limit(request.getLimit()).offset(request.getOffset())
                    .total(NumberUtils.LONG_ZERO).data(Collections.emptyList()).build();
        }

        List<UserHeaderBean> userHeaderBeans = queryUserHeaderBeans(request);
        return PageResponse.<UserHeaderBean>builder().limit(request.getLimit()).offset(request.getOffset())
                .total(count).data(userHeaderBeans).build();
    }


    public List<UserHeaderBean> queryUserHeaderBeans(QueryUserHeadersRequest request) {
        return headerImgService.queryHeaderImgListByRequest(request).stream().map(header -> {
            UserHeaderBean userHeaderImgBean = new UserHeaderBean();
            BeanUtils.copyProperties(header, userHeaderImgBean);
            userHeaderImgBean.setIsCurrentHeader(Status.ENABLE.getCode().equals(header.getStatus()));
            return userHeaderImgBean;
        }).collect(Collectors.toList());
    }


    public String getDefaultUserHeaderImg() {
        return defaultHeaderImg;
    }
}
