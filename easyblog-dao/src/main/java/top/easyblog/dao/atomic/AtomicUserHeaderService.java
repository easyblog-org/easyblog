package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.enums.Status;
import top.easyblog.common.request.header.QueryUserHeaderImgRequest;
import top.easyblog.common.request.header.QueryUserHeadersRequest;
import top.easyblog.dao.annotation.RecordNullable;
import top.easyblog.dao.auto.mapper.UserHeaderMapper;
import top.easyblog.dao.auto.model.UserHeader;
import top.easyblog.dao.auto.model.example.UserHeaderExample;
import top.easyblog.support.util.IdGenerator;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/30 10:53
 */
@Slf4j
@Service
public class AtomicUserHeaderService {

    @Autowired
    private UserHeaderMapper userHeaderMapper;


    public void createUserHeaderImgSelective(UserHeader record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setCode(IdGenerator.generateRandomCode(IdGenerator.DEFAULT_LENGTH));
        record.setStatus(Status.ENABLE.getCode());
        userHeaderMapper.insertSelective(record);
        log.info("[DB]Insert new header images: {}", JsonUtils.toJSONString(record));
    }


    @RecordNullable
    public UserHeader queryByRequest(QueryUserHeaderImgRequest request) {
        UserHeaderExample example = new UserHeaderExample();
        UserHeaderExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        }
        if (StringUtils.isNotBlank(request.getUserCode())) {
            criteria.andUserCodeEqualTo(request.getUserCode());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        return Iterables.getFirst(userHeaderMapper.selectByExample(example), null);
    }


    public List<UserHeader> queryHeaderImgListByRequest(QueryUserHeadersRequest request) {
        UserHeaderExample example = generateUserHeaderImgExamples(request);
        return userHeaderMapper.selectByExample(example);
    }

    public long countByRequest(QueryUserHeadersRequest request) {
        return userHeaderMapper.countByExample(generateUserHeaderImgExamples(request));
    }

    private UserHeaderExample generateUserHeaderImgExamples(QueryUserHeadersRequest request) {
        UserHeaderExample example = new UserHeaderExample();
        UserHeaderExample.Criteria criteria = example.createCriteria();
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
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }

        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        return example;
    }

    public void updateHeaderImgByRequest(UserHeader record) {
        record.setUpdateTime(new Date());
        userHeaderMapper.updateByPrimaryKeySelective(record);
        log.info("[DB]Update user header images: {}", JsonUtils.toJSONString(record));
    }

}
