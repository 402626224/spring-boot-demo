package com.imain.javademo.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImportBppElement14Service {
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    private CountDownLatch downloadPriceData = new CountDownLatch(1);
    private CountDownLatch importPriceData = new CountDownLatch(1);

    public void importData() {
        executor.execute(() -> {
            try {
                importBaseData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.execute(() -> {
            try {
                importPriceData();
                notifyGenSku();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void importBaseData() throws Exception {
//        1. 下载 base data 文件
//        2. 导入数据库
        System.out.println("-开始下载 baseData 文件");
        Thread.sleep(3000);
        System.out.println("-下载 baseData 文件完成；");
        downloadPriceData.countDown();

        System.out.println("-开始导入 baseData 文件");
        Thread.sleep(3000);
        System.out.println("-导入 baseData 文件完成；");
        importPriceData.countDown();
    }

    private void importPriceData() throws Exception {
//        1. 下载 price data 文件
//        2. 导入数据库
        System.out.println("wait 下载 priceData 文件");
        downloadPriceData.await();
        System.out.println("开始下载 priceData 文件");
        Thread.sleep(3000);
        System.out.println("下载 priceData 文件完成");

        System.out.println("wait 导入 priceData 文件");
        importPriceData.await();
        System.out.println("开始导入 priceData 文件");
        Thread.sleep(3000);
        System.out.println("导入 priceData 文件完成");
    }

    private void notifyGenSku() {
        System.out.println("通知生成sku");
    }

}
