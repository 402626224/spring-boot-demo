package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * author Songrui.Liu
 * date 2019/9/319:25
 */
@JsonRootName(value = "user")
public class UserWithRoot {

    private int id;
    private String name;

    public UserWithRoot() {
    }

    public UserWithRoot(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public UserWithRoot setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserWithRoot setName(String name) {
        this.name = name;
        return this;
    }
}
