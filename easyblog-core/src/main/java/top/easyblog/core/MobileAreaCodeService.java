package top.easyblog.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.easyblog.common.bean.MobileAreBean;
import top.easyblog.common.constant.Constants;
import top.easyblog.common.enums.ContinentEnum;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaListRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.convert.BeanMapper;
import top.easyblog.dao.atomic.AtomicMobileAreaCodeService;
import top.easyblog.dao.auto.model.MobileAreaCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:52
 */
@Slf4j
@Service
public class MobileAreaCodeService {

    @Autowired
    private AtomicMobileAreaCodeService atomicMobileAreaCodeService;

    @Autowired
    private BeanMapper beanMapper;

    @Value("${custom.batch-delete-password}")
    private String batchDeletePassword;

    public void createMobileArea(CreateMobileAreaRequest request) {
        MobileAreaCode mobileArea = atomicMobileAreaCodeService.queryPhoneAreaCodeByRequest(QueryMobileAreaRequest.builder()
                .crownCode(request.getCrownCode()).countryCode(request.getCountryCode()).build());
        if (Objects.nonNull(mobileArea)) {
            throw new BusinessException(EasyResultCode.PHONE_AREA_CODE_ALREADY_EXISTS);
        }

        ContinentEnum.codeOfOptional(request.getContinentCode());

        mobileArea = beanMapper.convertMobileAreaCodeCreateReq2MobileArea(request);
        atomicMobileAreaCodeService.insertPhoneAreaCodeByRequest(mobileArea);
    }


    public MobileAreBean queryMobileAreaDetails(QueryMobileAreaRequest request) {
        return Optional.ofNullable(atomicMobileAreaCodeService.queryPhoneAreaCodeByRequest(request))
                .map(item -> beanMapper.convertMobileArea2MobileAreaBean(item)).orElse(null);
    }


    public PageResponse<MobileAreBean> queryMobileAreaPage(QueryMobileAreaListRequest request) {
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页,默认查询前1000条数据
            request.setOffset(NumberUtils.INTEGER_ZERO);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.QUERY_LIMIT_ONE_THOUSAND : request.getLimit());
            List<MobileAreBean> phoneAreaCodeBeans = buildMobileAreaBeanList(request);
            return PageResponse.<MobileAreBean>builder()
                    .total((long) phoneAreaCodeBeans.size()).data(phoneAreaCodeBeans).limit(request.getLimit()).offset(request.getOffset()).build();
        }
        //分页
        long count = atomicMobileAreaCodeService.countByRequest(request);
        if (Objects.equals(NumberUtils.LONG_ZERO, count)) {
            return PageResponse.<MobileAreBean>builder().limit(request.getLimit()).offset(request.getOffset())
                    .total(NumberUtils.LONG_ZERO).data(Collections.emptyList()).build();
        }

        return PageResponse.<MobileAreBean>builder().limit(request.getLimit()).offset(request.getOffset())
                .total(count).data(buildMobileAreaBeanList(request)).build();
    }

    private List<MobileAreBean> buildMobileAreaBeanList(QueryMobileAreaListRequest request) {
        return atomicMobileAreaCodeService.queryPhoneAreaCodeListByRequest(request).stream().map(areaCode -> {
            return beanMapper.convertMobileArea2MobileAreaBean(areaCode);
        }).collect(Collectors.toList());
    }


    public void updateMobileArea(String code, UpdateMobileAreaRequest request) {
        MobileAreaCode areaCode = atomicMobileAreaCodeService.queryPhoneAreaCodeByRequest(QueryMobileAreaRequest.builder()
                .code(code).build());
        if (Objects.isNull(areaCode)) {
            throw new BusinessException(EasyResultCode.MOBILE_AREA_NOT_FOUND);
        }

        MobileAreaCode mobileAreaCode = beanMapper.convertMobileAreaUpdateReq2MobileArea(areaCode.getId(), request);
        atomicMobileAreaCodeService.updatePhoneAreaCodeByRequest(mobileAreaCode);
    }


    public void deleteByIds(List<String> codes, String password) {
        if (CollectionUtils.isEmpty(codes)) {
            log.info("Empty delete phone area code list,ignore operate.");
            return;
        }
        if (StringUtils.isBlank(password) || !StringUtils.equals(password, batchDeletePassword)) {
            throw new BusinessException(EasyResultCode.DELETE_OPERATION_NOT_PERMISSION);
        }
        atomicMobileAreaCodeService.deleteByIds(codes);
    }
}
