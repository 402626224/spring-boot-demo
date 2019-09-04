package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * author Songrui.Liu
 * date 2019/9/416:44
 */
public class JsonUnwrappedTests {

    @Test
    public void testJsonUnWrapped() throws JsonProcessingException {
        UnwrappedUser.Name name = new UnwrappedUser.Name();
        name.setFirstName("john").setLastName("Doe");
        UnwrappedUser user = new UnwrappedUser();
        user.setId(101).setName(name);

        String result = new ObjectMapper().writeValueAsString(user);
        System.out.println(result);
        /*
            no has @JsonUnwrapped
            {"id":101,"name":{"firstName":"john","lastName":"Doe"}}

            has @JsonUnwrapped
            {"id":101,"firstName":"john","lastName":"Doe"}
         */
    }

    public static class UnwrappedUser {
        public int id;

        //        @JsonUnwrapped
        public Name name;

        public static class Name {
            public String firstName;
            public String lastName;

            public String getFirstName() {
                return firstName;
            }

            public Name setFirstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public String getLastName() {
                return lastName;
            }

            public Name setLastName(String lastName) {
                this.lastName = lastName;
                return this;
            }
        }

        public int getId() {
            return id;
        }

        public UnwrappedUser setId(int id) {
            this.id = id;
            return this;
        }

        public Name getName() {
            return name;
        }

        public UnwrappedUser setName(Name name) {
            this.name = name;
            return this;
        }
    }
}
