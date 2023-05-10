package top.easyblog.dao.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.common.request.role.QueryUserRolesDetailsRequest;
import top.easyblog.common.request.role.QueryUserRolesListRequest;
import top.easyblog.common.request.role.UpdateUserRolesRequest;
import top.easyblog.dao.auto.mapper.UserRoleRelationshipMapper;
import top.easyblog.dao.auto.model.UserRoleRelationship;
import top.easyblog.dao.auto.model.example.UserRoleRelationshipExample;
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
public class AtomicUserRolesService {

    @Autowired
    private UserRoleRelationshipMapper mapper;


    public UserRoleRelationship insertOne(UserRoleRelationship record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB]Insert new user_role successfully!Details==>{}", JsonUtils.toJSONString(record));
        return record;
    }


    public void updateByPrimaryKey(UserRoleRelationship record) {
        record.setUpdateTime(new Date());
        mapper.updateByPrimaryKey(record);
        log.info("[DB]Update user_role successfully!Details==>{}", JsonUtils.toJSONString(record));
    }

    
    public UserRoleRelationship queryDetails(QueryUserRolesDetailsRequest request) {
        UserRoleRelationshipExample example = new UserRoleRelationshipExample();
        UserRoleRelationshipExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getRoleId())) {
            criteria.andRoleIdEqualTo(request.getRoleId());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        return Iterables.getFirst(mapper.selectByExample(example), null);
    }

    public List<UserRoleRelationship> queryList(QueryUserRolesListRequest request) {
        UserRoleRelationshipExample example = generateExamples(request);
        example.setLimit(request.getLimit());
        example.setOffset(request.getOffset());
        return mapper.selectByExample(example);
    }

    public long countByRequest(QueryUserRolesListRequest request) {
        UserRoleRelationshipExample example = generateExamples(request);
        return mapper.countByExample(example);
    }


    private UserRoleRelationshipExample generateExamples(QueryUserRolesListRequest request) {
        UserRoleRelationshipExample example = new UserRoleRelationshipExample();
        UserRoleRelationshipExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getUserIds())) {
            criteria.andUserIdIn(request.getUserIds());
        }
        if (CollectionUtils.isNotEmpty(request.getRolesIds())) {
            criteria.andRoleIdIn(request.getRolesIds());
        }
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        return example;
    }

    public void updateByExampleSelective(UserRoleRelationship UserRoleRelationship, UpdateUserRolesRequest request) {
        UserRoleRelationshipExample example = new UserRoleRelationshipExample();
        UserRoleRelationshipExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getRoleId())) {
            criteria.andRoleIdEqualTo(request.getRoleId());
        }
        mapper.updateByExampleSelective(UserRoleRelationship, example);
    }

    public void deleteByExample(UpdateUserRolesRequest request) {
        UserRoleRelationshipExample example = new UserRoleRelationshipExample();
        UserRoleRelationshipExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getRoleId())) {
            criteria.andRoleIdEqualTo(request.getRoleId());
        }
        mapper.deleteByExample(example);
    }
}
