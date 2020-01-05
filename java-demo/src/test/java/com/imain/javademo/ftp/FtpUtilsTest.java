package com.imain.javademo.ftp;

import com.imain.javademo.JavaDemoApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FtpUtilsTest extends JavaDemoApplicationTests {

    @Autowired
    private FtpUtils ftpUtils;

    @Test
    public void init() {
        ftpUtils.init();
    }

    @Test
    public void downloadFile() throws IOException {
        ftpUtils.init();
        List<String> fileNames = new ArrayList<>();
        ftpUtils.list("/", fileNames);
        String fileName = getLastFileName(fileNames);
        boolean isSuc = ftpUtils.downloadFile("/", fileName.substring(1), "/data/csv/");
        System.out.println("文件下载结果：" + isSuc);
    }

    @Test
    public void deleteFile() {
    }

    @Test
    public void list() {
    }

    @Test
    public void list1() throws IOException {
        ftpUtils.init();
        List<String> fileNames = new ArrayList<>();
        ftpUtils.list("/", fileNames);
        fileNames.forEach(m -> {
            System.out.println(m);
        });
        System.out.println(getLastFileName(fileNames));
    }

    public String getLastFileName(List<String> fileNames) {
        return fileNames.get(fileNames.size() - 1);
    }
}