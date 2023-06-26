package com.example.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyHttp {


    public static String getJsonData(String url) {
        try {
            Response response = new OkHttpClient().newCall(
                    new Request.Builder()
                            .url(url)
                            .build()
            ).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Bitmap getBitmap(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() != null) {
                InputStream inputStream = response.body().byteStream();
                return BitmapFactory.decodeStream(inputStream);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
