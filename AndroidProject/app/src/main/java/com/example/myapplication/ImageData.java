package com.example.myapplication;

import android.graphics.Bitmap;

public class ImageData {
    private final String title;
    private final String copyright;
    private final Bitmap image_src;

    public ImageData(String title, String copyright, Bitmap bitmap) {
        this.title = title;
        this.copyright = copyright;
        this.image_src = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public String getCopyright() {
        return copyright;
    }

    public Bitmap getImage_src() {
        return image_src;
    }
}
