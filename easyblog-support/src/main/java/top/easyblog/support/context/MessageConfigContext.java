package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.TemplateValueConfigBean;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-22 20:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageConfigContext {
    private String businessModule;

    private String businessEvent;

    private String group;

    private Integer priority;

    private Byte channel;

    private List<MessageConfigBean> configs;

    private String expectPushTime;

    private Byte idType;

    private Byte msgType;

    private Byte shieldType;

    private String title;

    private String businessMessage;

    private String msgTemplateContent;

}
