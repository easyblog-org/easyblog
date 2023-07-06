package top.easyblog.core;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.ArticleBean;
import top.easyblog.common.bean.UserAvatarBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.QuerySection;
import top.easyblog.common.enums.Status;
import top.easyblog.common.request.header.CreateUserHeaderRequest;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.common.request.header.QueryUserHeadersRequest;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicUserHeaderService;
import top.easyblog.dao.auto.model.UserHeader;
import top.easyblog.service.section.IArticleSectionInquireService;
import top.easyblog.service.section.IUserSectionInquireService;
import top.easyblog.support.context.ArticleSectionContext;
import top.easyblog.support.context.UserSectionContext;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:19
 */
@Service
public class UserAvatarService implements
        IArticleSectionInquireService, IUserSectionInquireService {

    @Autowired
    private AtomicUserHeaderService headerImgService;

    @Value("${custom.default-header-image}")
    private String defaultHeaderImg;

    @Autowired
    private BeanMapper beanMapper;

    public void createUserHeader(CreateUserHeaderRequest request) {
        UserHeader UserHeader = headerImgService.queryByRequest(QueryUserHeaderImgRequest.builder()
                .userCode(request.getUserCode()).status(Status.ENABLE.getCode()).build());
        // 更新用户之前的头像状态为失效
        Optional.ofNullable(UserHeader).ifPresent(imgBean -> {
            UserHeader.setStatus(Status.DISABLE.getCode());
            headerImgService.updateHeaderImgByRequest(UserHeader);
        });
        if (StringUtils.isBlank(request.getHeaderImgUrl())) {
            // 设置默认头像
            request.setHeaderImgUrl(defaultHeaderImg);
        }

        UserHeader userHeader = beanMapper.convertUserHeaderCreateReq2UserHeader(request);
        headerImgService.createUserHeaderImgSelective(userHeader);
    }

    public UserAvatarBean queryUserHeaderDetails(QueryUserHeaderImgRequest request) {
        UserHeader UserHeader = headerImgService.queryByRequest(request);
        return Optional.ofNullable(UserHeader).map(item -> {
            UserAvatarBean userHeaderImgBean = new UserAvatarBean();
            BeanUtils.copyProperties(UserHeader, userHeaderImgBean);
            return userHeaderImgBean;
        }).orElse(null);
    }

    public PageResponse<UserAvatarBean> queryUserHeaderList(QueryUserHeadersRequest request) {
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            // 不分页，默认查询1000条数据
            request.setOffset(NumberUtils.INTEGER_ZERO);
            request.setLimit(
                    Objects.isNull(request.getLimit()) ? Constants.QUERY_LIMIT_MAX_THOUSAND : request.getLimit());
            List<UserAvatarBean> userHeaderImgBeans = queryUserHeaderBeans(request);
            return PageResponse.<UserAvatarBean>builder()
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .total((long) userHeaderImgBeans.size())
                    .data(userHeaderImgBeans)
                    .build();
        }

        long count = headerImgService.countByRequest(request);
        if (count == 0) {
            return PageResponse.<UserAvatarBean>builder().limit(request.getLimit()).offset(request.getOffset())
                    .total(NumberUtils.LONG_ZERO).data(Collections.emptyList()).build();
        }

        List<UserAvatarBean> userHeaderBeans = queryUserHeaderBeans(request);
        return PageResponse.<UserAvatarBean>builder().limit(request.getLimit()).offset(request.getOffset())
                .total(count).data(userHeaderBeans).build();
    }

    public List<UserAvatarBean> queryUserHeaderBeans(QueryUserHeadersRequest request) {
        return headerImgService.queryHeaderImgListByRequest(request).stream().map(header -> {
            UserAvatarBean userHeaderImgBean = new UserAvatarBean();
            BeanUtils.copyProperties(header, userHeaderImgBean);
            userHeaderImgBean.setIsCurrentHeader(Status.ENABLE.getCode().equals(header.getStatus()));
            return userHeaderImgBean;
        }).collect(Collectors.toList());
    }

    public String getDefaultUserHeaderImg() {
        return defaultHeaderImg;
    }

    @Override
    public void execute(String section, ArticleSectionContext ctx, List<ArticleBean> articleBeanList,
                        boolean queryWhenSectionEmpty) {
        if (CollectionUtils.isEmpty(articleBeanList)) {
            return;
        }

        List<String> authorIds = articleBeanList.stream().map(ArticleBean::getAuthorId).collect(Collectors.toList());
        if (StringUtils.containsIgnoreCase(QuerySection.QUERY_ARTICLE_AUTHOR_AVATAR.name(), section)
                || queryWhenSectionEmpty) {
            ctx.setAuthorAvatarBeanMap(buildUserAvatarMap(authorIds, (userHeaderBeans) -> userHeaderBeans.stream()
                    .filter(item -> Boolean.TRUE.equals(item.getIsCurrentHeader()))
                    .collect(Collectors.toMap(UserAvatarBean::getUserCode, Function.identity(), (e1, e2) -> e1))));
        }
    }

    @Override
    public void execute(String section, UserSectionContext ctx, Map<Long, String> userIdCodesMap,
                        boolean queryWhenSectionEmpty) {
        if (MapUtils.isEmpty(userIdCodesMap)) {
            return;
        }

        List<String> userCodes = new ArrayList<>(userIdCodesMap.values());
        if (StringUtils.containsIgnoreCase(QuerySection.QUERY_HEADER_IMG.getName(), section) || queryWhenSectionEmpty) {
            ctx.setUserHistoryImagesMap(
                    buildUserAvatarMap(userCodes, (userHeaders) -> userHeaders.stream().filter(Objects::nonNull)
                            .collect(Collectors.groupingBy(UserAvatarBean::getUserCode))));
        }
        if (StringUtils.containsIgnoreCase(QuerySection.QUERY_CURRENT_HEADER_IMG.getName(), section) || queryWhenSectionEmpty) {
            ctx.setUserCurrentImagesMap(buildUserAvatarMap(userCodes, (userHeaderBeans) -> userHeaderBeans.stream()
                    .filter(item -> Boolean.TRUE.equals(item.getIsCurrentHeader()))
                    .collect(Collectors.toMap(UserAvatarBean::getUserCode, Function.identity(), (e1, e2) -> e1))));
        }
    }

    private <R> R buildUserAvatarMap(List<String> userCodes,
                                     Function<List<UserAvatarBean>, R> groupFunction) {
        if (Objects.isNull(groupFunction))
            return null;

        QueryUserHeadersRequest queryUserHeadersRequest = QueryUserHeadersRequest.builder()
                .userCodes(userCodes).status(Status.ENABLE.getCode()).build();
        List<UserAvatarBean> userHeaderBeans = queryUserHeaderBeans(queryUserHeadersRequest);
        return groupFunction.apply(userHeaderBeans);
    }

}
