package com.android.dmk78.guessthestar;

import android.graphics.Bitmap;

public class Star {
    private String name;
    private String img;

    public Star(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }




}
