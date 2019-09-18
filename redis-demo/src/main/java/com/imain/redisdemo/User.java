package com.imain.redisdemo;

import java.io.Serializable;

/**
 * author Songrui.Liu
 * date 2019/9/1719:41
 */
public class User implements Serializable {

    private String name;
    private String age;


    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public User setAge(String age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
