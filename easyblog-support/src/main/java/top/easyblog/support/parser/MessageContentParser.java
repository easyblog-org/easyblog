package top.easyblog.support.parser;

import java.io.StringWriter;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

public class MessageContentParser {
    
    private JetEngine jetTemplateEngine;
     

    public MessageContentParser(){
         //创建模板引擎对象
        this.jetTemplateEngine=JetEngine.create();
    }

    /**
     * 解析模板中的参数内容并填充参数
     * @param template   模板字符串
     * @param data       模板参数数据
     * @return 可用的完整消息内容
     */
    public String parseMessageContent(String template, Map<String,Object> data){
        JetTemplate compiledTemplate = jetTemplateEngine.createTemplate(template);
        StringWriter writer = new StringWriter();
        compiledTemplate.render(data, writer);
        return writer.toString();
    }

}
