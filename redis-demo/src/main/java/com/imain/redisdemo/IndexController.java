package com.imain.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author Songrui.Liu
 * date 2019/9/1214:21
 */
@Controller
@RequestMapping
public class IndexController {

    @Value("${spring.redis.host}")
    private String host;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private String redisKey = "akey";

    @ResponseBody
    @RequestMapping("/")
    public String v() {
        return String.valueOf(redisTemplate.opsForValue().get("akey"));
    }

    @ResponseBody
    @RequestMapping("/set")
    public String set(String value) {
        redisTemplate.opsForValue().set(redisKey, value);
        return String.valueOf(redisTemplate.opsForValue().get(redisKey));
    }

}
