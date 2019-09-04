package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * author Songrui.Liu
 * date 2019/9/318:36
 */
public class JacksonBeanTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonBeanTests.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testJsonAnyGetter() throws JsonProcessingException {
        ExtendableBean extendableBean = ExtendableBean.getInstance();
        String jsonStr = objectMapper.writeValueAsString(extendableBean);
        LOGGER.info(jsonStr);

        /**
         *  no has @JsonAnyGetter
         *  {"json":{"attr":false},"properties":{"name":"tom","age":"12"},"pName":"exBean"}
         *
         *  has @JsonAnyGetter  ( properties  与 name  同一层次 )
         *  {"json":{"attr":false},"pName":"exBean","name":"tom","age":"12"}
         */
    }


    @Test
    public void testJsonGetter() throws JsonProcessingException {
        ExtendableBean bean = ExtendableBean.getInstance();
        String jsonStr = objectMapper.writeValueAsString(bean);
        LOGGER.info(jsonStr);

        /**
         *  no has @JsonGetter("pName")
         *  {"properties":{"name":"tom","age":"12"},"name":"exBean"}
         *
         *  has @JsonGetter("pName")
         * {"properties":{"name":"tom","age":"12"},"pName":"exBean"}
         */
    }

    @Test
    public void testJsonValue() throws JsonProcessingException {
        String jsonStr = objectMapper.writeValueAsString(TypeEnumWithValue.TYPE1);
        LOGGER.info(jsonStr);

        jsonStr = objectMapper.writeValueAsString(TypeEnumWithValue.values());
        LOGGER.info(jsonStr);

        /**
         * no has @JsonValue
         * "TYPE1"
         * ["TYPE1","TYPE2"]
         *
         * has @JsonValue
         * "Type A"
         * ["Type A","Type B"]
         */
    }


    @Test
    public void testJsonRawValue() throws JsonProcessingException {
        ExtendableBean bean = ExtendableBean.getInstance();
        String jsonStr = objectMapper.writeValueAsString(bean);
        LOGGER.info(jsonStr);

        /**
         *  no has @JsonRawValue
         *  {"json":"{\"attr\":false}","properties":{"name":"tom","age":"12"}}
         *
         * has @JsonRawValue
         *  {"json":{"attr":false},"properties":{"name":"tom","age":"12"},"pName":"exBean","name":"tom","age":"12"}
         */
    }

    @Test
    public void testJsonRootName() throws JsonProcessingException {
        UserWithRoot userWithRoot = new UserWithRoot(101, "userName");
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String jsonStr = objectMapper.writeValueAsString(userWithRoot);
        LOGGER.info(jsonStr);
        /**
         *  no has @JsonRootName(value = "user")
         *  {"id":101,"name":"userName"}
         *
         *  has @JsonRootName(value = "user")
         *  {"user":{"id":101,"name":"userName"}}
         */
    }

    @JsonRootName(value = "user")
    public static class ExtendableBean {
        private String name;

        @JsonRawValue
        private String json;
        private Map<String, String> properties;

        public static ExtendableBean getInstance() {
            Map map = new HashMap();
            map.put("name", "tom");
            map.put("age", "12");
            return new ExtendableBean().setName("exBean").setJson("{\"attr\":false}").setProperties(map);
        }

        public String getJson() {
            return json;
        }

        public ExtendableBean setJson(String json) {
            this.json = json;
            return this;
        }

        @JsonGetter("pName")
        public String getName() {
            return name;
        }

        public ExtendableBean setName(String name) {
            this.name = name;
            return this;
        }

        @JsonAnyGetter
        public Map<String, String> getProperties() {
            return properties;
        }

        public ExtendableBean setProperties(Map<String, String> properties) {
            this.properties = properties;
            return this;
        }

    }


}
