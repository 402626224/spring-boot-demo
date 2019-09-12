package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imain.jacksondemo.jsoncomponent.Color;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 该@JsonManagedReference和@JsonBackReference注释可以处理父/子关系和解决循环。
 * author Songrui.Liu
 * date 2019/9/419:34
 */
public class JsonReferenceTests {

    /**
     * TypeReference
     *
     * @throws IOException
     */
    @Test
    public void testTypeReference() throws IOException {
        List<Color> list = new ArrayList<>();
        list.add(new Color(1, 1, 1));
        list.add(new Color(2, 2, 2));
        list.add(new Color(3, 3, 3));

        String json = new ObjectMapper().writeValueAsString(list);
        System.out.println(json);

        List<Color> result = new ObjectMapper().readValue(json, new TypeReference<List<Color>>() {
        });
//        List<Color> result = new ObjectMapper().readValue(json, new TypeReference<List<Color>>() {});
        System.out.println(result);

        Color color = new Color(1, 2, 3);
        String jsonStr = new ObjectMapper().writeValueAsString(color);
        System.out.println(jsonStr);

        Map map = new ObjectMapper().readValue(jsonStr, Map.class);
        System.out.println(map.get("blue"));

    }


    @Test
    public void whenSerializingUsingJacksonReferenceAnnotation_thenCorrect()
            throws JsonProcessingException {
        UserWithRef user = new UserWithRef(1, "John");
        ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);

        String result = new ObjectMapper().writeValueAsString(item);

        System.out.println(result);

//        {"id":2,"itemName":"book","owner":{"id":1,"name":"John"}}

//        assertThat(result, containsString("book"));
//        assertThat(result, containsString("John"));
//        assertThat(result, not(containsString("userItems")));
    }

    public static class ItemWithRef {
        public int id;
        public String itemName;

        @JsonManagedReference
        public UserWithRef owner;

        public ItemWithRef(int id, String itemName, UserWithRef owner) {
            this.id = id;
            this.itemName = itemName;
            this.owner = owner;
        }

        public int getId() {
            return id;
        }

        public ItemWithRef setId(int id) {
            this.id = id;
            return this;
        }

        public String getItemName() {
            return itemName;
        }

        public ItemWithRef setItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public UserWithRef getOwner() {
            return owner;
        }

        public ItemWithRef setOwner(UserWithRef owner) {
            this.owner = owner;
            return this;
        }
    }

    public static class UserWithRef {
        public int id;
        public String name;

        public UserWithRef(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonBackReference
        public List<ItemWithRef> userItems = new ArrayList<>();

        public void addItem(ItemWithRef item) {
            userItems.add(item);
        }

        public int getId() {
            return id;
        }

        public UserWithRef setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public UserWithRef setName(String name) {
            this.name = name;
            return this;
        }

        public List<ItemWithRef> getUserItems() {
            return userItems;
        }

        public UserWithRef setUserItems(List<ItemWithRef> userItems) {
            this.userItems = userItems;
            return this;
        }
    }

}
