package com.imain.javademo.model;

/**
 * author Songrui.Liu
 * date 2019/12/1117:18
 */
public class Student {

    private String name;
    private Integer age;
    private Integer stature;

    public Student() {
    }

    public Student(String name, Integer age, Integer stature) {
        this.name = name;
        this.age = age;
        this.stature = stature;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getStature() {
        return stature;
    }

    public Student setStature(Integer stature) {
        this.stature = stature;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", stature=" + stature +
                '}';
    }
}
