package com.hui.apiversion.controller.v2;

import com.hui.apiversion.annotion.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TestControllerV2")
@ApiVersion(2)
@RequestMapping("{version}/test")
public class TestController {
    /**
     * 覆盖老版本的test/index方法
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "v2 - index -> "+ System.currentTimeMillis();
    }
}
