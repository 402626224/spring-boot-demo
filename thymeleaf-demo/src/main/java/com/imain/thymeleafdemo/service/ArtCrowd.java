package com.imain.thymeleafdemo.service;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import com.github.liaochong.myexcel.core.annotation.ExcelTable;
import com.github.liaochong.myexcel.core.annotation.ExcludeColumn;

import java.time.LocalDateTime;

/**
 * author Songrui.Liu
 * date 2019/12/414:00
 */
@ExcelTable(sheetName = "艺术生")
public class ArtCrowd {

    @ExcelColumn(order = 0, title = "姓名")
    private String name;

    @ExcelColumn(order = 1, title = "年龄")
    private Integer age;

    @ExcelColumn(order = 2, title = "性别")
    private String gender;

    @ExcelColumn(order = 3, title = "绘画等级")
    private String paintingLevel;

    @ExcelColumn(order = 4, title = "是否会跳舞")
    private boolean dance;

    @ExcelColumn(order = 5, title = "考核时间", dateFormatPattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assessmentTime;

    @ExcludeColumn
    private String hobby;

    public String getName() {
        return name;
    }

    public ArtCrowd setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public ArtCrowd setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public ArtCrowd setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getPaintingLevel() {
        return paintingLevel;
    }

    public ArtCrowd setPaintingLevel(String paintingLevel) {
        this.paintingLevel = paintingLevel;
        return this;
    }

    public boolean isDance() {
        return dance;
    }

    public ArtCrowd setDance(boolean dance) {
        this.dance = dance;
        return this;
    }

    public LocalDateTime getAssessmentTime() {
        return assessmentTime;
    }

    public ArtCrowd setAssessmentTime(LocalDateTime assessmentTime) {
        this.assessmentTime = assessmentTime;
        return this;
    }

    public String getHobby() {
        return hobby;
    }

    public ArtCrowd setHobby(String hobby) {
        this.hobby = hobby;
        return this;
    }
}
