package top.easyblog.core.strategy.template.impl;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.JsonPath;

import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.TemplateValueConfigBean;
import top.easyblog.common.enums.TemplateValueConfigType;
import top.easyblog.core.strategy.template.TemplateParameterParseStrategy;
import top.easyblog.support.context.MessageParseContext;
import top.easyblog.support.util.DocumentUtils;

/**
 * @author: frank.huang
 * @date: 2023-04-25 19:51
 */
@Component
public class DirectJsonValueParameterParseStrategy implements TemplateParameterParseStrategy {

    @Override
    public byte getParameterType() {
        return TemplateValueConfigType.DIRECT_JSON_VALUE.getCode();
    }

    @Override
    public Pair<String, Object> doParse(MessageParseContext context) {
        MessageConfigBean configBean = context.getConfig();
        TemplateValueConfigBean templateValueConfig = configBean.getTemplateValueConfig();
        String values = DocumentUtils.parse(context.getBusinessMessage(), templateValueConfig.getExpression());
        return Pair.of(configBean.getName(), values);
    }
}
