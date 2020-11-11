package com.hui.apiversion.controller.v1;

import com.hui.apiversion.annotion.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TestControllerV1")
@ApiVersion
@RequestMapping("{version}/test")
public class TestController {
    @RequestMapping("/index")
    public String index(){
        return "v1 - index -> " + System.currentTimeMillis();
    }

    /**
     * 新版本不存在时，会调用此方法，如: http://127.0.0.1:8080/v2/test/extend
     * @return
     */
    @RequestMapping("/extend")
    public String extend(){
        return "v1 -  extend -> " + System.currentTimeMillis();
    }
}
