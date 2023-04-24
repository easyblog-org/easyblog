package top.easyblog.support.parser.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jetbrick.template.JetEngine;
import top.easyblog.support.parser.MessageContentParser;

@Configuration
@ConditionalOnClass({JetEngine.class})
public class MessageContentParserAutoConfig {
    
    /**
     * 自动配置，将MessageContentParser交给Spring容器管理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageContentParser  messageContentParser(){
        return new MessageContentParser();
    }

}
