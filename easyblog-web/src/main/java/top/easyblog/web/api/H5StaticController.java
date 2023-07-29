package top.easyblog.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.easyblog.core.StaticService;
import top.easyblog.web.annotation.ResponseWrapper;

/**
 * @author: frank.huang
 * @date: 2023-07-29 20:06
 */
@RequestMapping("/h5/v1/static")
@RestController
public class H5StaticController {

    @Autowired
    private StaticService staticService;

    @ResponseWrapper
    @GetMapping("/article-events")
    public Object queryArticleReportEvent() {
        return staticService.queryArticleReportEvent();
    }

}
