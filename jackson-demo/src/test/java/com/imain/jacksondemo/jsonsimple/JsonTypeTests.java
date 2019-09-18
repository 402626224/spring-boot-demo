package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * author Songrui.Liu
 * date 2019/9/416:26
 */
public class JsonTypeTests {

    @Test
    public void jsonType() throws JsonProcessingException {
        Zoo.Dog dog = new Zoo.Dog("lacy");
        Zoo zoo = new Zoo(dog);

        String result = new ObjectMapper()
                .writeValueAsString(zoo);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("dog"));

        System.out.println(result);

    }

    @Test
    public void testr1() throws IOException {
        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        String jsonStr = om.writeValueAsString(new UserWithRoot().setId(111).setName("name"));
        System.out.println(jsonStr);

        om.readValue(jsonStr, new TypeReference<Object>() {
        });
    }


}
