package com.imain.jacksondemo.jsonsimple;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * author Songrui.Liu
 * date 2019/9/319:12
 */
public enum TypeEnumWithValue {
    TYPE1(1, "Type A"), TYPE2(2, "Type B");

    private Integer id;
    private String name;

    TypeEnumWithValue(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }


}
