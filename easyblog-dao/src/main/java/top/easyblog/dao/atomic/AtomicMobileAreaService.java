package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.mobilearea.QueryMobileAreaListRequest;
import top.easyblog.common.request.mobilearea.QueryMobileAreaRequest;
import top.easyblog.dao.auto.mapper.MobileAreaCodeMapper;
import top.easyblog.dao.auto.model.MobileAreaCode;
import top.easyblog.dao.auto.model.example.MobileAreaCodeExample;
import top.easyblog.support.util.IdGenerator;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:11
 */
@Slf4j
@Service
public class AtomicMobileAreaService {

    @Autowired
    private MobileAreaCodeMapper mobileAreaCodeMapper;


    public void insertPhoneAreaCodeByRequest(MobileAreaCode mobileAreaCode) {
        mobileAreaCode.setCreateTime(new Date());
        mobileAreaCode.setUpdateTime(new Date());
        mobileAreaCode.setCode(IdGenerator.generateRandomCode(IdGenerator.DEFAULT_LENGTH));
        mobileAreaCodeMapper.insertSelective(mobileAreaCode);
        log.info("[DB] insert new mobile area:{}", JsonUtils.toJSONString(mobileAreaCode));
    }

    public MobileAreaCode queryPhoneAreaCodeByRequest(QueryMobileAreaRequest request) {
        MobileAreaCodeExample example = new MobileAreaCodeExample();
        MobileAreaCodeExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        }
        if (StringUtils.isNotBlank(request.getCrownCode())) {
            criteria.andCrownCodeEqualTo(request.getCrownCode());
        }
        if (StringUtils.isNotBlank(request.getCountryCode())) {
            criteria.andCountryCodeEqualTo(request.getCountryCode());
        }
        if (StringUtils.isNotBlank(request.getAreaName())) {
            criteria.andAreaNameEqualTo(request.getAreaName());
        }
        return Iterables.getFirst(mobileAreaCodeMapper.selectByExample(example), null);
    }


    public List<MobileAreaCode> queryPhoneAreaCodeListByRequest(QueryMobileAreaListRequest request) {
        MobileAreaCodeExample example = generatePhoneAreaCodeExamples(request);
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        return mobileAreaCodeMapper.selectByExample(example);
    }

    public long countByRequest(QueryMobileAreaListRequest request) {
        return mobileAreaCodeMapper.countByExample(generatePhoneAreaCodeExamples(request));
    }

    private MobileAreaCodeExample generatePhoneAreaCodeExamples(QueryMobileAreaListRequest request) {
        MobileAreaCodeExample example = new MobileAreaCodeExample();
        MobileAreaCodeExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getCodes())) {
            criteria.andCodeIn(request.getCodes());
        }
        if (StringUtils.isNotBlank(request.getCountryCode())) {
            criteria.andCountryCodeEqualTo(request.getCountryCode());
        }
        if (StringUtils.isNotBlank(request.getAreaName())) {
            criteria.andAreaNameLike("%" + request.getAreaName() + "%");
        }

        return example;
    }

    public void updatePhoneAreaCodeByRequest(MobileAreaCode areaCode) {
        areaCode.setUpdateTime(new Date());
        mobileAreaCodeMapper.updateByPrimaryKeySelective(areaCode);
        log.info("[DB] update area code:{}", JsonUtils.toJSONString(areaCode));
    }

    public void deleteByIds(List<String> codes) {
        MobileAreaCodeExample example = new MobileAreaCodeExample();
        MobileAreaCodeExample.Criteria criteria = example.createCriteria();
        criteria.andCodeIn(codes);
        mobileAreaCodeMapper.deleteByExample(example);
        log.info("[DB] batch delete mobile area code by code:{}", codes);
    }
}
