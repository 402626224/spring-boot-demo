package com.imain.jacksondemo.jsoncomponent;

/**
 * author Songrui.Liu
 * date 2019/9/317:20
 */
public class Color {

    private Integer red;
    private Integer blue;
    private Integer green;

    public Integer getRed() {
        return red;
    }

    public Color setRed(Integer red) {
        this.red = red;
        return this;
    }

    public Integer getBlue() {
        return blue;
    }

    public Color setBlue(Integer blue) {
        this.blue = blue;
        return this;
    }

    public Integer getGreen() {
        return green;
    }

    public Color setGreen(Integer green) {
        this.green = green;
        return this;
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", blue=" + blue +
                ", green=" + green +
                '}';
    }
}
