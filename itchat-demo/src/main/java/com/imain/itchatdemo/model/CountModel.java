package com.imain.itchatdemo.model;

/**
 * author Songrui.Liu
 * date 2019/12/1317:52
 */
public class CountModel {

    //"count":{"all":"0","good":"0","normal":"0","bad":"0","pic":"0"}

    private String all;
    private String good;
    private String normal;
    private String bad;
    private String pic;

    public String getAll() {
        return all;
    }

    public CountModel setAll(String all) {
        this.all = all;
        return this;
    }

    public String getGood() {
        return good;
    }

    public CountModel setGood(String good) {
        this.good = good;
        return this;
    }

    public String getNormal() {
        return normal;
    }

    public CountModel setNormal(String normal) {
        this.normal = normal;
        return this;
    }

    public String getBad() {
        return bad;
    }

    public CountModel setBad(String bad) {
        this.bad = bad;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public CountModel setPic(String pic) {
        this.pic = pic;
        return this;
    }

    @Override
    public String toString() {
        return "CountModel{" +
                "all='" + all + '\'' +
                ", good='" + good + '\'' +
                ", normal='" + normal + '\'' +
                ", bad='" + bad + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
