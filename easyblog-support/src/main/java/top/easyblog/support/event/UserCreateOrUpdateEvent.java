package top.easyblog.support.event;

import lombok.Getter;
import top.easyblog.support.context.CreateOrRefreshUserRoleContext;

@Getter
public class UserCreateOrUpdateEvent extends EasyApplicationContextEvent{

    private CreateOrRefreshUserRoleContext context;

    public UserCreateOrUpdateEvent(CreateOrRefreshUserRoleContext context) {
        super(context);
        this.context = context;
    }
    
}
