package top.easyblog.core.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.bean.UserHeaderBean;

import java.util.List;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2023-02-24 22:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserSectionContext {
    // 当前头像
    private Map<Long, UserHeaderBean> userCurrentImagesMap;
    // 历史头像
    private Map<Long, List<UserHeaderBean>> userHistoryImagesMap;
    // 账户
    private Map<Long, List<AccountBean>> accountsMap;
    // 角色
    private Map<Long, List<RolesBean>> rolesMap;
    // 登录记录
    private Map<Long, List<LoginLogBean>> signInLogsMap;
}
