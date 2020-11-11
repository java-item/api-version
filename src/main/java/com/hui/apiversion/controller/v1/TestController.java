package com.hui.apiversion.controller.v1;

import com.hui.apiversion.annotion.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TestControllerV1")
@ApiVersion
@RequestMapping("{version}/test")
public class TestController {
    @RequestMapping("index")
    public String index(){
        return "v1 -> " + System.currentTimeMillis();
    }
}
