package com.example.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile {
    public static void saveImage(String fileName, Bitmap bitmap, Context context) {
        File fileDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/saved_images/");
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        File file = new File(fileDirectory, fileName + ".jpg");
        FileOutputStream outputStream;
        try {
//            outputStream = new FileOutputStream(fileDirectory.getAbsolutePath() + "/" + "image_" + System.currentTimeMillis() + ".png");
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show();
    }
}
