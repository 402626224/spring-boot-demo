package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * author Songrui.Liu
 * date 2019/9/416:57
 */
public class JsonViewTests {

    @Test
    public void whenSerializingUsingJsonView_thenCorrect()
            throws JsonProcessingException {
        Item item = new Item(2, "book", "John");

        String result = new ObjectMapper()
                .writerWithView(Views.Internal.class)
                .writeValueAsString(item);

        System.out.println(result);
        /**
         *  Views.Public
         *  {"id":2,"itemName":"book"}
         *
         *  Views.Internal
         *  {"id":2,"itemName":"book","ownerName":"John"}
         */
    }

    public static class Item {
        @JsonView(Views.Public.class)
        public int id;

        @JsonView(Views.Public.class)
        public String itemName;

        @JsonView(Views.Internal.class)
        public String ownerName;

        public Item(int id, String itemName, String ownerName) {
            this.id = id;
            this.itemName = itemName;
            this.ownerName = ownerName;
        }

        public int getId() {
            return id;
        }

        public Item setId(int id) {
            this.id = id;
            return this;
        }

        public String getItemName() {
            return itemName;
        }

        public Item setItemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public Item setOwnerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }
    }

    public static class Views {
        public static class Public {
        }

        public static class Internal extends Public {
        }
    }


}
