package com.limb.customview.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.limb.customview.R;
import com.limb.customview.utils.utils;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b1234a);
        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
        int scaleRatio = 10;
        int blurRadius = 20;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio,
                originBitmap.getHeight() / scaleRatio,
                false);
        Bitmap blurBitmap = utils.doBlur(scaledBitmap, blurRadius, true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(blurBitmap);
    }

}
