package top.easyblog.core.processor.chian.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.core.processor.chian.MessageProcessChain;
import top.easyblog.dao.atomic.AtomicMessageSendRecordService;
import top.easyblog.dao.atomic.AtomicMessageTemplateService;
import top.easyblog.dao.auto.model.MessageTemplate;
import top.easyblog.support.context.MessageProcessorContext;
import top.easyblog.support.parser.MessageContentParser;
import top.easyblog.support.util.JsonUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 消息组装处理器，把消息和模板组装起来
 *
 * @author: frank.huang
 * @date: 2023-02-11 17:50
 */
@Slf4j
@Component
public class MessageAssembleProcessChain implements MessageProcessChain {

    @Autowired
    private AtomicMessageSendRecordService atomicMessageSendRecordService;

    @Autowired
    private AtomicMessageTemplateService atomicMessageTemplateService;

    @Autowired
    private MessageContentParser messageContentParser;

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public MessageProcessorContext process(MessageProcessorContext context) {
        MessageTemplate messageTemplate = atomicMessageTemplateService.queryByRequest(QueryMessageTemplateRequest.builder()
                .templateCode(context.getTemplateCode())
                .build());
        if (Objects.isNull(messageTemplate)) {
            throw new BusinessException(EasyResultCode.TEMPLATE_NOT_FOUND);
        }

        Map<String, Object> variables = JsonUtils.jsonToMap(context.getReplaceValues());
        String parsedContent = messageContentParser.parseMessageContent(messageTemplate.getMsgContent(), variables);
        context.setContent(parsedContent);
        saveAssembledRecord(context);
        return context;
    }


    private void saveAssembledRecord(MessageProcessorContext context) {
       /* BusinessMessageRecord messageSendRecord = new BusinessMessageRecord();
        messageSendRecord.set
        messageSendRecord.setChannel(context.getChannel());
        messageSendRecord.setReceiver(context.getReceiver());
        messageSendRecord.setSender(context.getSender());
        messageSendRecord.setBusinessMessage(context.getContent());
        messageSendRecord.setN(context.getTitle());
        messageSendRecord.setStatus(MessageSendStatus.INITIALIZED.getCode());
        atomicMessageSendRecordService.create(messageSendRecord);*/
    }
}
