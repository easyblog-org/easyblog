package top.easyblog.core.strategy.template.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import top.easyblog.common.enums.TemplateValueConfigType;
import top.easyblog.core.strategy.template.TemplateParameterParseStrategy;
import top.easyblog.support.context.MessageParseContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 19:51
 */
@Component
public class InterfaceDirectValueParameterParseStrategy implements TemplateParameterParseStrategy {

    @Override
    public byte getParameterType() {
        return TemplateValueConfigType.INTERFACE_DIRECT_VALUE.getCode();
    }

    @Override
    public Pair<String, Object> doParse(MessageParseContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doParse'");
    }

    
}
