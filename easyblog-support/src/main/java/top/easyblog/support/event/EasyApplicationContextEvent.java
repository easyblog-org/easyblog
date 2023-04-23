package top.easyblog.support.event;

import org.springframework.context.ApplicationEvent;

public abstract class EasyApplicationContextEvent extends ApplicationEvent{

    public EasyApplicationContextEvent(Object source) {
        super(source);
    }
    
}
