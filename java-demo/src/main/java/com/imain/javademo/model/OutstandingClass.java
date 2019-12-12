package com.imain.javademo.model;

import java.util.List;

/**
 * author Songrui.Liu
 * date 2019/12/1120:47
 */
public class OutstandingClass {

    private String name ;

    private List<Student> students;

    public String getName() {
        return name;
    }

    public OutstandingClass setName(String name) {
        this.name = name;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public OutstandingClass setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    @Override
    public String toString() {
        return "OutstandingClass{" +
                "name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}
