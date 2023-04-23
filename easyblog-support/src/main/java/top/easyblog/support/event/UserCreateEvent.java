package top.easyblog.support.event;

import lombok.Data;
import lombok.Getter;

@Getter
public class UserCreateEvent extends EasyApplicationContextEvent{

    private CreateOrRefreshUserRoleContext context;

    public UserCreateEvent(CreateOrRefreshUserRoleContext context) {
        super(context);
        this.context = context;
    }
    
}
