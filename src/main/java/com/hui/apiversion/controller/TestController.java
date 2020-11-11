package com.hui.apiversion.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/index")
    public String index(){
        return "无版本 - index -> " + System.currentTimeMillis();
    }
}
