package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.phoneauth.QueryPhoneAuthRequest;
import top.easyblog.dao.auto.mapper.PhoneAuthMapper;
import top.easyblog.dao.auto.model.PhoneAuth;
import top.easyblog.dao.auto.model.example.PhoneAuthExample;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:13
 */
@Slf4j
@Service
public class AtomicPhoneAuthService {
    @Autowired
    private PhoneAuthMapper phoneAuthMapper;


    public Long insertByRequestSelective(PhoneAuth record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        phoneAuthMapper.insertSelective(record);
        log.info("[DB] insert new phone auth account:{}", JsonUtils.toJSONString(record));
        return record.getId();
    }


    public PhoneAuth queryPhoneAuthByRequest(QueryPhoneAuthRequest request) {
        PhoneAuthExample example = new PhoneAuthExample();
        PhoneAuthExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        /*if (StringUtils.isNotBlank(request.getPhoneAreaCode())) {
            criteria.andPhoneAreaCodeEqualTo(request.getPhoneAreaCode());
        }*/
        if (StringUtils.isNotBlank(request.getPhone())) {
            criteria.andPhoneEqualTo(request.getPhone());
        }
        return Iterables.getFirst(phoneAuthMapper.selectByExample(example), null);
    }

    public void updatePhoneAuthByRequest(PhoneAuth phoneAuth) {
        phoneAuth.setUpdateTime(new Date());
        phoneAuthMapper.updateByPrimaryKey(phoneAuth);
        log.info("[DB] update phone auth account:{}", JsonUtils.toJSONString(phoneAuth));
    }

}
