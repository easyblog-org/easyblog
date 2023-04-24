package top.easyblog.dao.custom;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.request.message.config.QueryMessageConfigsRequest;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-05 14:08
 */
@Mapper
public interface MyMessageConfigMapper {

    long countByRequest(@Param("request") QueryMessageConfigsRequest request);

    List<MessageConfigBean> selectListByRequest(@Param("request") QueryMessageConfigsRequest request);

}
