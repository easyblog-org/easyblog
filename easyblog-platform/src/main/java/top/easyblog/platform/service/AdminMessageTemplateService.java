package top.easyblog.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.common.bean.MessageTemplateBean;
import top.easyblog.common.enums.MessageTemplateStatus;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.request.message.template.CreateMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplateRequest;
import top.easyblog.common.request.message.template.QueryMessageTemplatesRequest;
import top.easyblog.common.request.message.template.UpdateMessageTemplateRequest;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.common.response.PageResponse;
import top.easyblog.core.MessageTemplateService;
import top.easyblog.dao.auto.model.MessageTemplate;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-05-01 18:53
 */
@Service
public class AdminMessageTemplateService {

    @Autowired
    private MessageTemplateService messageTemplateService;

    /**
     * 创建消息模板
     *
     * @param request
     */
    public void create(CreateMessageTemplateRequest request) {
        messageTemplateService.createMessageTemplate(request);
    }

    /**
     * 更新消息模板
     *
     * @param code
     * @param request
     */
    public void update(String code, UpdateMessageTemplateRequest request) {
        //重新编辑之后需要将模板置为未发布状态，需要重新发布之后修改才会生效
        request.setStatus(MessageTemplateStatus.DRAFT.getCode());
        messageTemplateService.updateMessageTemplate(code, request);
    }


    /**
     * 更新消息模板状态
     *
     * @param code
     * @param status
     */
    public void updateStatus(String code, Short status) {
        MessageTemplateBean messageTemplateBean = details(QueryMessageTemplateRequest.builder()
                .templateCode(code).build());
        if (Objects.isNull(messageTemplateBean)) {
            throw new BusinessException(EasyResultCode.TEMPLATE_NOT_FOUND);
        }

        messageTemplateService.updateMessageTemplate(messageTemplateBean.getTemplateCode(), UpdateMessageTemplateRequest.builder()
                .status(status).build());
    }


    /**
     * 查询消息模板详情
     *
     * @param request
     * @return
     */
    public MessageTemplateBean details(QueryMessageTemplateRequest request) {
        return messageTemplateService.details(request);
    }

    /**
     * 查询消息模板列表
     *
     * @param request
     * @return
     */
    public PageResponse<MessageTemplateBean> list(QueryMessageTemplatesRequest request) {
        return messageTemplateService.list(request);
    }

}
