package com.imain.thymeleafdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author Songrui.Liu
 * date 2019/10/1220:40
 */
@RestController
@RequestMapping
public class LoggerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);

    @RequestMapping("logger")
    public String logger() {
        LOGGER.info("this. is logger msg");
        return "success";
    }


    @RequestMapping("loggerError")
    public String loggerError() {
        try {
            throw new Exception("bizExcetion");
        } catch (Exception e) {
            LOGGER.error("error info ", e);
        }
        return "success";
    }

}
