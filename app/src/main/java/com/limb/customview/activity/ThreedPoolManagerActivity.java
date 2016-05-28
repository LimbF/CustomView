package com.limb.customview.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.limb.customview.R;
import com.limb.customview.utils.SmsObserver;
import com.limb.customview.utils.ThreadPoolManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreedPoolManagerActivity extends AppCompatActivity {

    private ThreadPoolManager tm;

    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threed_pool_manager);
        imageview = (ImageView)findViewById(R.id.iv_threed_pool_manager_img);
        tm = ThreadPoolManager.getInstance();
        String url = "http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";
        MyRunnable myRunnable = new MyRunnable(url);
//        new Thread(myRunnable).start();
        tm.addTask(myRunnable);
        SmsObserver smsObserver = new SmsObserver(new Handler(),this);
        getContentResolver().registerContentObserver(Uri.parse("content://sms"), true,
                smsObserver);
    }
    class MyRunnable implements Runnable {
        private String urlStr;
        public MyRunnable(String urlStr){
            this.urlStr = urlStr;
        }
        @Override
        public void run() {
            try {
                URL url = new URL(urlStr); // path图片的网络地址
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                BufferedInputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageview.setImageBitmap(bitmap);// 加载到ImageView上
                        }
                    });
                } else {
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
