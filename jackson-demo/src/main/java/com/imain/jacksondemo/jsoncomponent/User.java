package com.imain.jacksondemo.jsoncomponent;

/**
 * author Songrui.Liu
 * date 2019/9/316:43
 */
public class User {

    private Color favoriteColor;

    public Color getFavoriteColor() {
        return favoriteColor;
    }

    public User setFavoriteColor(Color favoriteColor) {
        this.favoriteColor = favoriteColor;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "favoriteColor=" + favoriteColor +
                '}';
    }
}
