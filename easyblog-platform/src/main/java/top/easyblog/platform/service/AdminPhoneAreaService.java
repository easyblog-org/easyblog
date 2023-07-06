package top.easyblog.platform.service;

import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.common.bean.MobileAreBean;
import top.easyblog.common.bean.MobileAreaCodeMapBean;
import top.easyblog.common.enums.ContinentEnum;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.mobilearea.CreateMobileAreaRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaListRequest;
import top.easyblog.common.request.mobilearea.UpdateMobileAreaRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.MobileAreaService;

@Slf4j
@Service
public class AdminPhoneAreaService {

    @Autowired
    private MobileAreaService mobileAreaService;

    /**
     * 创建手机国际区号映射
     * 
     * @param request
     */
    public void create(CreateMobileAreaRequest request) {
        mobileAreaService.createMobileArea(request);
    }

    /**
     * 查询手机国际区号映射map
     * 
     * @param request
     * @return
     */
    public MobileAreaCodeMapBean queryPhoneAreaCodeMap(QueryMobileAreaListRequest request) {
        ContinentEnum continentEnum = ContinentEnum.codeOfOptional(request.getContinentCode()).orElse(null);
        if (Objects.isNull(continentEnum)) {
            log.info("Query param continent_code cannot be empty or is invalid.");
            throw new BusinessException(EasyResultCode.INVALID_CONTINENT_TYPE);
        }
        PageResponse<MobileAreBean> response = mobileAreaService.queryMobileAreaPage(request);
        List<MobileAreBean> phoneAreaCodeBeans = response.getData();
        if (CollectionUtils.isEmpty(phoneAreaCodeBeans)) {
            return null;
        }
        MobileAreaCodeMapBean phoneAreaCodeMapBean = new MobileAreaCodeMapBean();
        phoneAreaCodeMapBean.setContinentCode(continentEnum.getCode());
        phoneAreaCodeMapBean.setContinentName(continentEnum.getDesc());
        phoneAreaCodeMapBean.setChildren(phoneAreaCodeBeans);
        phoneAreaCodeMapBean.setTotal(response.getTotal());
        return phoneAreaCodeMapBean;
    }

    /**
     * 更新手机国际区号信息
     * 
     * @param phoneAreaCodeId
     * @param request
     */
    public void update(String phoneAreaCodeId, UpdateMobileAreaRequest request) {
        mobileAreaService.updateMobileArea(phoneAreaCodeId, request);
    }

    /**
     * 根据手机国际区号编号删除区号
     * 
     * @param ids
     * @param password
     */
    public void deleteByIds(List<String> ids, String password) {
        mobileAreaService.deleteByIds(ids, password);
    }

}
