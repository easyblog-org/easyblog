package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.role.QueryRolesDetailsRequest;
import top.easyblog.common.request.role.QueryRolesListRequest;
import top.easyblog.dao.auto.mapper.RolesMapper;
import top.easyblog.dao.auto.model.Roles;
import top.easyblog.dao.auto.model.example.RolesExample;
import top.easyblog.support.util.IdGenerator;
import top.easyblog.support.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:07
 */
@Slf4j
@Service
public class AtomicRolesService {

    @Autowired
    private RolesMapper mapper;


    public Roles insertOne(Roles record) {
        record.setCode(IdGenerator.generateRandomCode(IdGenerator.DEFAULT_LENGTH));
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB]Insert new role successfully!Details==>{}", JsonUtils.toJSONString(record));
        return record;
    }


    public void updateByPrimaryKey(Roles record) {
        record.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(record);
        log.info("[DB]Update role by pk successfully!Details==>{}", JsonUtils.toJSONString(record));
    }


    public Roles queryDetails(QueryRolesDetailsRequest request) {
        if (StringUtils.isBlank(request.getCode()) && Objects.nonNull(request.getName())) {
            return null;
        }
        RolesExample example = new RolesExample();
        RolesExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        }
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
       /* if (Objects.nonNull(request.getName())) {
            criteria.andNameLike("%"+request.getName()+"%");
        }*/
        return Iterables.getFirst(mapper.selectByExample(example), null);
    }

    public List<Roles> queryList(QueryRolesListRequest request) {
        RolesExample example = generateExamples(request);
        example.setLimit(request.getLimit());
        example.setOffset(request.getOffset());
        return mapper.selectByExample(example);
    }

    public long countByRequest(QueryRolesListRequest request) {
        RolesExample example = generateExamples(request);
        return mapper.countByExample(example);
    }


    private RolesExample generateExamples(QueryRolesListRequest request) {
        RolesExample example = new RolesExample();
        RolesExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getCodes())) {
            criteria.andCodeIn(request.getCodes());
        }
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }
       /* if (StringUtils.isNotBlank(request.getName())) {
            criteria.andNameLike("%" + request.getName() + "%");
        }*/
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        return example;
    }
}
