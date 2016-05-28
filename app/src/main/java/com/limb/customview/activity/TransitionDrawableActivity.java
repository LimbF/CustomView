package com.limb.customview.activity;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.limb.customview.R;

public class TransitionDrawableActivity extends AppCompatActivity {

    private ImageView img_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_drawable);
        img_show = (ImageView)findViewById(R.id.iv_transition_drawable_img);
        TransitionDrawable td = (TransitionDrawable) img_show.getDrawable();
        td.startTransition(500);
    }

}
