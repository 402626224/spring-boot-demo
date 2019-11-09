package com.imain.thymeleafdemo;

import com.imain.thymeleafdemo.service.SnowflakeIdWorker;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.util.UUID;

/**
 * author Songrui.Liu
 * date 2019/10/169:50
 */
public class SnowflakeTests {

    @Test
    public void test() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
//            System.out.println(Long.toBinaryString(id));
            System.out.println(id + " -> " + Base64Utils.encodeToString(String.valueOf(id).getBytes()));
        }
    }

    @Test
    public void testUUID() {
        //SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            String id = UUID.randomUUID().toString();
//            System.out.println(Long.toBinaryString(id));
            System.out.println(id + " -> " + Base64Utils.encodeToUrlSafeString(id.getBytes()));
            System.out.println(id + " -> " + Base64Utils.encodeToString(id.getBytes()));
        }
    }
}
