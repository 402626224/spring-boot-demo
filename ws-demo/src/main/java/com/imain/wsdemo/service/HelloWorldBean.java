package com.imain.wsdemo.service;

import org.springframework.stereotype.Component;

/**
 * author Songrui.Liu
 * date 2019/11/2910:41
 */
@Component("helloWorld")
public class HelloWorldBean implements HelloWorld {
    public String greeting(String name) {
        return "你好 "+name;
    }

    public String print() {
        return "我叫林计钦";
    }
}
