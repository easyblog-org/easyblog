package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.common.bean.MessageConfigBean;

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

    private String msgGroup;

    private Integer priority;

    private Byte channel;

    private List<MessageConfigBean> configs; 

    private String expectPushTime;

    private Byte idType;

    private Byte sendChannel;

    private Byte msgType;

    private Byte shieldType;

    private String msgTemplateContent;

}
