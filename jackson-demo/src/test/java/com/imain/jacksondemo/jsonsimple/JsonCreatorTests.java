package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * author Songrui.Liu
 * date 2019/9/410:25
 */
public class JsonCreatorTests {

    @Test
    public void testJsonCreator() throws IOException {
        String json = "{\"id\":1,\"theName\":\"My bean\"}";

        BeanWithCreator bean = new ObjectMapper()
                .readerFor(BeanWithCreator.class)
                .readValue(json);

        System.out.println(bean);
    }


    public static class BeanWithCreator {
        private int id;
        private String name;

        @JsonCreator
        public BeanWithCreator(
                @JsonProperty("id") int id,
                @JsonProperty("theName") String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public BeanWithCreator setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public BeanWithCreator setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "BeanWithCreator{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}
