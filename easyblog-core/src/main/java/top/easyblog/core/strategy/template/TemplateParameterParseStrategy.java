package top.easyblog.core.strategy.template;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.TemplateValueConfigBean;
import top.easyblog.common.enums.TemplateValueConfigType;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;
import top.easyblog.support.context.MessageParseContext;

/**
 * @author: frank.huang
 * @date: 2023-04-25 19:43
 */
public interface TemplateParameterParseStrategy {

    /**
     * 获取模板参数取值方式类型
     *
     * @return
     */
    byte getParameterType();

    /**
     * 解析模板参数
     *
     * @param context 参数Bean
     * @return 模板参数name已经对应value
     */
    default Pair<String, Object> parse(MessageParseContext context) {
        MessageConfigBean configBean = context.getConfig();
        TemplateValueConfigBean templateValueConfig = null;
        if (Objects.isNull(configBean) || Objects.isNull(templateValueConfig = configBean.getTemplateValueConfig())) {
            throw new BusinessException(EasyResultCode.ILLEGAL_PARAM, "Message config bean can not be null");
        }

        if (Objects.equals(TemplateValueConfigType.DIRECT_JSON_VALUE.getCode(), templateValueConfig.getType()) ||
                Objects.equals(TemplateValueConfigType.DIRECT_VALUE.getCode(), templateValueConfig.getType()) &&
                        StringUtils.isBlank(context.getBusinessMessage())) {
            throw new BusinessException(EasyResultCode.ILLEGAL_PARAM, "Biz message value can not be empty when template value is '" + templateValueConfig.getType() + "'");
        }
        return doParse(context);
    }

    Pair<String, Object> doParse(MessageParseContext context);

}
