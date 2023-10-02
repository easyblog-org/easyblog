package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.loginlog.QueryLoginLogListRequest;
import top.easyblog.common.request.loginlog.QueryLoginLogRequest;
import top.easyblog.dao.auto.mapper.LoginLogMapper;
import top.easyblog.dao.auto.model.LoginLog;
import top.easyblog.dao.auto.model.example.LoginLogExample;
import top.easyblog.dao.custom.mapper.MyLoginLogMapper;
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
public class AtomicLoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private MyLoginLogMapper myLoginLogMapper;


    public LoginLog insertLoginLogByRequest(LoginLog record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setCode(IdGenerator.generateRandomCode(IdGenerator.DEFAULT_LENGTH));
        loginLogMapper.insertSelective(record);
        log.info("[DB] insert new login in log:{}", JsonUtils.toJSONString(record));
        return record;
    }

    public LoginLog querySignLogByRequest(QueryLoginLogRequest request) {
        LoginLogExample example = new LoginLogExample();
        LoginLogExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        }
        if (StringUtils.isNotBlank(request.getUserCode())) {
            criteria.andUserCodeEqualTo(request.getUserCode());
        }
        if (StringUtils.isNotBlank(request.getAccountCode())) {
            criteria.andAccountCodeEqualTo(request.getAccountCode());
        }
        if (StringUtils.isNotBlank(request.getToken())) {
            criteria.andTokenEqualTo(request.getToken());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        return Iterables.getFirst(loginLogMapper.selectByExample(example), null);
    }

    public List<LoginLog> querySignInLogListByRequest(QueryLoginLogListRequest request) {
        LoginLogExample example = generateExamples(request);
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        example.setOrderByClause(" create_time desc");
        return loginLogMapper.selectByExample(example);
    }

    public long countByRequest(QueryLoginLogListRequest request) {
        return loginLogMapper.countByExample(generateExamples(request));
    }

    private LoginLogExample generateExamples(QueryLoginLogListRequest request) {
        LoginLogExample example = new LoginLogExample();
        LoginLogExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        } else if (CollectionUtils.isNotEmpty(request.getCodes())) {
            criteria.andCodeIn(request.getCodes());
        }
        if (StringUtils.isNotBlank(request.getUserCode())) {
            criteria.andUserCodeEqualTo(request.getUserCode());
        } else if (CollectionUtils.isNotEmpty(request.getUserCodes())) {
            criteria.andUserCodeIn(request.getUserCodes());
        }
        if (StringUtils.isNotBlank(request.getAccountCode())) {
            criteria.andAccountCodeEqualTo(request.getAccountCode());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        } else if (CollectionUtils.isNotEmpty(request.getStatuses())) {
            criteria.andStatusIn(request.getStatuses());
        }

        return example;
    }

    public void updateLoginLogByPrimarySelective(LoginLog loginLog) {
        loginLog.setUpdateTime(new Date());
        loginLogMapper.updateByPrimaryKeySelective(loginLog);
        log.info("[DB]Update sign_in_log: {}", JsonUtils.toJSONString(loginLog));
    }

}
