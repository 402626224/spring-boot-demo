package com.imain.myexceldemo.controller;

import com.github.liaochong.myexcel.core.DefaultExcelBuilder;
import com.github.liaochong.myexcel.utils.AttachmentExportUtil;
import com.imain.myexceldemo.excelvo.ArtCrowd;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * author Songrui.Liu
 * date 2019/12/617:03
 */
@Controller()
@RequestMapping("/excel")
public class ExcelController {

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
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
