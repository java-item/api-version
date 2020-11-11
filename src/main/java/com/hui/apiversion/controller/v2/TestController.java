package com.hui.apiversion.controller.v2;

import com.hui.apiversion.annotion.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TestControllerV2")
@ApiVersion(2)
@RequestMapping("{version}/test")
public class TestController {
    @RequestMapping("index")
    public String index(){
        return "v2 -> "+ System.currentTimeMillis();
    }
}
