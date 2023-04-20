package top.easyblog.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import top.easyblog.web.annotation.ResponseWrapper;

/**
* 健康检查
*/
@RequestMapping("/status")
@RestController
public class HealthController {

    @ResponseWrapper
    @GetMapping(value = "")
    public String health() {
        return "success";
    }
}
