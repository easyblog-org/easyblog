package top.easyblog.dao.atomic;


import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.account.QueryAccountListRequest;
import top.easyblog.common.request.account.QueryAccountRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.AccountMapper;
import top.easyblog.dao.auto.model.Account;
import top.easyblog.dao.auto.model.example.AccountExample;
import top.easyblog.support.util.IdGenerator;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:02
 */
@Slf4j
@Service
public class AtomicAccountService {

    @Autowired
    private AccountMapper accountMapper;


    public Account insertSelective(Account record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setCode(IdGenerator.generateRandomCode(IdGenerator.DEFAULT_LENGTH));
        log.info("[DB] Insert account:{}", JsonUtils.toJSONString(record));
        accountMapper.insertSelective(record);
        return record;
    }

    @RecordNullable
    public Account queryAccountByRequest(QueryAccountRequest request) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        }
        if (Objects.nonNull(request.getUserCode())) {
            criteria.andUserCodeEqualTo(request.getUserCode());
        }
        if (Objects.nonNull(request.getIdentityType())) {
            criteria.andIdentityTypeEqualTo(request.getIdentityType());
        }
        if (StringUtils.isNotBlank(request.getIdentifier())) {
            criteria.andIdentifierEqualTo(request.getIdentifier());
        }
        if (StringUtils.isNotBlank(request.getCredential())) {
            criteria.andCredentialEqualTo(request.getCredential());
        }
        return Iterables.getFirst(accountMapper.selectByExample(example), null);
    }

    public List<Account> queryAccountListByRequest(QueryAccountListRequest request) {
        AccountExample example = generateExamples(request);
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        return accountMapper.selectByExample(example);
    }

    private AccountExample generateExamples(QueryAccountListRequest request) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        if (CollectionUtils.isNotEmpty(request.getUserCodes())) {
            criteria.andUserCodeIn(request.getUserCodes());
        }
        if (StringUtils.isNotBlank(request.getIdentifier())) {
            criteria.andIdentifierEqualTo(request.getIdentifier());
        }
        if (Objects.nonNull(request.getIdentityType())) {
            criteria.andIdentityTypeEqualTo(request.getIdentityType());
        }
        if (Objects.nonNull(request.getVerified())) {
            criteria.andVerifiedEqualTo(request.getVerified());
        }

        return example;
    }


    public long countByRequest(QueryAccountListRequest request) {
        AccountExample example = generateExamples(request);
        return accountMapper.countByExample(example);
    }

    public void updateAccountByPKSelective(Account account) {
        account.setUpdateTime(new Date());
        accountMapper.updateByPrimaryKeySelective(account);
        log.info("[DB] update account[id={}]:{}", account.getId(), JsonUtils.toJSONString(account));
    }

}
