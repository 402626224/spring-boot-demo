package com.imain.elasticsearchdemo.controller;

import com.imain.elasticsearchdemo.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author Songrui.Liu
 * date 2019/11/1119:35
 */
@RestController
public class IndexController {


    @RequestMapping("/")
    public String index() {
        return "SUCCESS";
    }
}
