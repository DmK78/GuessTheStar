package com.android.dmk78.guessthestar;

import android.graphics.Bitmap;

public class Star {
    private String name;

    private Bitmap img;

    public Star(String name, Bitmap img) {
        this.name = name;

        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Star{" +
                "name='" + name + '\'' +
                ", img=" + img +
                '}';
    }
}
