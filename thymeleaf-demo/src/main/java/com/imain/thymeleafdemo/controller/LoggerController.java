package com.imain.thymeleafdemo.controller;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.imain.thymeleafdemo.service.ArtCrowd;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * author Songrui.Liu
 * date 2019/10/12 20:40
 */
@RestController
@RequestMapping
public class LoggerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerController.class);

    @RequestMapping("body")
    public String body(@RequestBody String body) {
        return body;
    }

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

    @GetMapping("/example")
    public void defaultBuild(HttpServletResponse response) throws Exception {
        List<ArtCrowd> dataList = this.getDataList();
        Workbook workbook = DefaultExcelBuilder.of(ArtCrowd.class).build(dataList);
        AttachmentExportUtil.export(workbook, "艺术生信息", response);
    }

    private List<ArtCrowd> getDataList() {
        List<ArtCrowd> dataList = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            ArtCrowd artCrowd = new ArtCrowd();
            artCrowd.setName("李四");
            artCrowd.setAge(18);
            artCrowd.setGender("Woman");
            artCrowd.setPaintingLevel("一级证书");
            artCrowd.setDance(true);
            artCrowd.setAssessmentTime(LocalDateTime.now());
            artCrowd.setHobby("钓鱼");
            dataList.add(artCrowd);
        }
        return dataList;
    }

}
