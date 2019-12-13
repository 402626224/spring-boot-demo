package com.imain.itchatdemo.model;

import java.util.Arrays;

/**
 * author Songrui.Liu
 * date 2019/12/1317:54
 */
public class ResultModel {

    private CountModel count;

    private double percent;

    private Object[] list;

    private String url;

    public CountModel getCount() {
        return count;
    }

    public ResultModel setCount(CountModel count) {
        this.count = count;
        return this;
    }

    public double getPercent() {
        return percent;
    }

    public ResultModel setPercent(double percent) {
        this.percent = percent;
        return this;
    }

    public Object[] getList() {
        return list;
    }

    public ResultModel setList(Object[] list) {
        this.list = list;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ResultModel setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "count=" + count +
                ", percent=" + percent +
                ", list=" + Arrays.toString(list) +
                ", url='" + url + '\'' +
                '}';
    }
}
