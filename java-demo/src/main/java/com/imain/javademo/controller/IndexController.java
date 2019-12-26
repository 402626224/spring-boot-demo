package com.imain.javademo.controller;

import com.imain.javademo.downloadfile.DownLoadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author Songrui.Liu
 * date 2019/12/26 19:11
 */
@RestController
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    DownLoadManager downLoadManager;

    @RequestMapping("/")
    public String index() throws Exception {
        long start = System.currentTimeMillis();
        downLoadManager.doDownload();
        LOGGER.info("useTime :{}ms", System.currentTimeMillis() - start);
        return "success";
    }
}
