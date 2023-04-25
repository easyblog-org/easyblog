package top.easyblog.core.strategy.template;


import org.apache.commons.lang3.tuple.Pair;
import top.easyblog.common.bean.MessageConfigBean;
import top.easyblog.common.bean.TemplateValueConfigBean;

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
     * @param templateValueConfigBean  参数Bean
     * @return 模板参数name已经对应value
     */
    Pair<String, Object> parse(MessageConfigBean templateValueConfigBean);


}
