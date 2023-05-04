package top.easyblog.support.parser;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * @author: frank.huang
 * @date: 2023-05-03 18:23
 */
public class MessageContentParserTest {

    public static void main(String[] args) {
        String template="<div style=\"margin:0;padding:0;background-color:#f8f8f8\">\n" +
                "    <div style=\"margin:10px 10px\">\n" +
                "       <p>亲爱的<b> ${username} </b>, 欢迎加入 EasyBlog !</p>\n" +
                "    <p>当您收到这封信的时候，您已经可以正常登录了。</p>\n" +
                "    <p>请点击链接登录首页: <a href='${homepage}'>${homepage}</a></p>\n" +
                "    <p>如果您的 email 程序不支持链接点击，请将上面的地址拷贝至您的浏览器(如IE)的地址栏进入。</p>\n" +
                "    <p>如果您还想申请管理员权限，可以联系管理员 ${email}</p>\n" +
                "    <p>我们对您产生的不便，深表歉意。</p>\n" +
                "    <p>希望您在 EasyBlog 系统度过快乐的时光!</p>\n" +
                "    <p></p>\n" +
                "    <p>-----------------------</p>\n" +
                "    <p></p>\n" +
                "    <p>(这是一封自动产生的email，请勿回复。)</p>\n" +
                "    </div>\n" +
                "</div>";

        MessageContentParser parser = new MessageContentParser();
        HashMap<String, Object> params = Maps.newHashMap();
        params.put("username","Frank Huang");
        params.put("homepage","https://www.easyblog.top");
        params.put("email","easyblog-notification@163.com");
        System.out.println(parser.parseMessageContent(template, params));
    }



}
