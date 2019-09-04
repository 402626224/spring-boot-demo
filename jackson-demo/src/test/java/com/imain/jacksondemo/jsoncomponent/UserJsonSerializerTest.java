package com.imain.jacksondemo.jsoncomponent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imain.jacksondemo.JacksonDemoApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * author Songrui.Liu
 * date 2019/9/316:55
 */
public class UserJsonSerializerTest extends JacksonDemoApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerialization() throws JsonProcessingException {
        User user = new User();
        user.setFavoriteColor(new Color().setBlue(1).setGreen(2).setRed(3));
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
    }

    @Test
    public void testDeserialize() throws IOException {
        String json = "{\"favoriteColor\":\"#f0f8ff\"}";
        User user = objectMapper.readValue(json, User.class);
        System.out.println(user);
    }

}
