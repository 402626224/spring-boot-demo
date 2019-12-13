package com.imain.itchatdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imain.itchatdemo.model.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * itchat4j: https://github.com/yaphone/itchat4j
 * author Songrui.Liu
 * date 2019/12/1317:35
 */
@RestController
@RequestMapping()
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String index() throws Exception {
        long time = System.currentTimeMillis();
        //http://shop225.wqmeng.cn/app/index.php?i=21&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail.get_comments&id=792&_=1576230047878
        String url = "http://shop225.wqmeng.cn/app/index.php?i=21&c=entry&m=ewei_shopv2&do=mobile&r=goods.detail.get_comments&id=792&_=" + String.valueOf(time);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36 QBCore/4.0.1278.400 QQBrowser/9.0.2524.400 Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2875.116 Safari/537.36 NetType/WIFI MicroMessenger/7.0.5 WindowsWechat";
        List<String> cookies = new ArrayList<String>();
        /* 登录获取Cookie 这里是直接给Cookie，可使用下方的login方法拿到Cookie给入*/
        //Cookie: PHPSESSID=9c1d769548767af2ada999b8333ecf2a; PHPSESSID=9c1d769548767af2ada999b8333ecf2a
        cookies.add("PHPSESSID=9c1d769548767af2ada999b8333ecf2a");       //在 header 中存入cookies
        cookies.add("PHPSESSID=9c1d769548767af2ada999b8333ecf2a");

        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);        //将cookie存入头部
        headers.set(HttpHeaders.USER_AGENT, userAgent);
        HttpEntity<String> entity = new HttpEntity<>(url, headers);

        ResponseEntity<String> obj = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String resp = obj.getBody();

        ProductModel productModel = new ObjectMapper().readValue(resp, ProductModel.class);

        String all = Optional.ofNullable(productModel).map(p -> p.getResult()).map(r -> r.getCount()).map(c -> c.getAll()).orElse("0");
        if (Objects.equals("0", all)) {
            logger.info("all : 0");
        }

        logger.info(productModel.toString());
        return resp;
    }


}
