package com.imain.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author Songrui.Liu
 * date 2019/9/1214:37
 */
@Controller
@RequestMapping
public class ObjestController {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @ResponseBody
    @RequestMapping("obj")
    public User setObj() {
        redisTemplate.opsForValue().set("aUser", new User().setAge("12").setName("tom"));
        return redisTemplate.opsForValue().get("aUser");
    }

}
