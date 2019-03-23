package com.android.dmk78.guessthestar;

import android.graphics.Bitmap;

public class Star {
    private String name;
    private String url;
    private Bitmap img;

    public Star(String name, String url, Bitmap img) {
        this.name = name;
        this.url = url;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
