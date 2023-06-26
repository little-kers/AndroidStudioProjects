package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.utils.MyHttp;
import com.example.myapplication.utils.SaveFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsherActivity extends AppCompatActivity {
    private static final String bingAPIURL = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    private static final String main_url = "https://www.bing.com";
    public static final String FILE_NAME = "test_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ushering);
        Button skip = findViewById(R.id.usher_button);
        ImageView imageView = findViewById(R.id.usher_image);
        TextView title = findViewById(R.id.usher_title);
        TextView copyright = findViewById(R.id.usher_copyright);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1000);
        imageView.setAnimation(alphaAnimation);
        Intent intent = new Intent(UsherActivity.this, MainActivity.class);
        imageViewSetImageSrc(new OnImageDataLoadedListener() {
            @Override
            public void onImageDataLoaded(ImageData imageData) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(imageData.getTitle());
                        copyright.setText(imageData.getCopyright());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageView.setImageBitmap(imageData.getImage_src());
                    }
                });
            }
        });
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                skip.setText("跳过" + l / 1000 + "s");
            }

            @Override
            public void onFinish() {
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                startActivity(intent);
            }
        });
    }

    public interface OnImageDataLoadedListener {
        void onImageDataLoaded(ImageData imageData);
    }

    private void imageViewSetImageSrc(OnImageDataLoadedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String title;
                String copyright;
                String url;
                String jsonData = MyHttp.getJsonData(bingAPIURL);
                Log.d("TAG", "run() returned: " + jsonData);
                if (jsonData != null) {
                    try {
                        JSONArray images = new JSONObject(jsonData).getJSONArray("images");
                        String string = images.get(0).toString();
                        title = new JSONObject(string).getString("title");
                        copyright = new JSONObject(string).getString("copyright");
                        url = new JSONObject(string).getString("url");
                        Bitmap bitmap = MyHttp.getBitmap(main_url + url);
                        listener.onImageDataLoaded(new ImageData(title, copyright, bitmap));

//                        if (Build.VERSION.SDK_INT >= 23) {
//                            int REQUEST_CODE_CONTACT = 101;
//                            String[] permissions = {
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                            //验证vm是否许可权限
//                            for (String str : permissions) {
//                                if (UsherActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                                    //申请权限
//                                    UsherActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                                    return;
//                                } else {
//                                    //这里就是权限打开之后自己要操作的逻辑
//                                    SaveFile.saveImage(FILE_NAME, bitmap, UsherActivity.this);
//                                }
//                            }
//                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();
    }
}