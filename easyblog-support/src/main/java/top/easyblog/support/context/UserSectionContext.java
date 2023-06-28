package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.bean.AccountBean;
import top.easyblog.common.bean.LoginLogBean;
import top.easyblog.common.bean.RolesBean;
import top.easyblog.common.bean.UserAvatarBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户Code和选项参数结果对应的上下文信息

 * @author: frank.huang
 * @date: 2023-02-24 22:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSectionContext {
    // 当前头像
    private Map<String, UserAvatarBean> userCurrentImagesMap;
    // 历史头像
    private Map<String, List<UserAvatarBean>> userHistoryImagesMap;
    // 账户
    private Map<String, List<AccountBean>> accountsMap;
    // 角色
    private Map<Long, List<RolesBean>> rolesMap;
    // 登录记录
    private Map<String, List<LoginLogBean>> signInLogsMap;



    public <K,V> V getSectionOptional(Map<K, V> contextMap, K key) {
        return Optional.ofNullable(contextMap).map(map -> map.get(key)).orElse(null);
    }
}
