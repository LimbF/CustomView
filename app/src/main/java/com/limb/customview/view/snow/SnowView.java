package com.limb.customview.view.snow;

/**
 * Created by limb on 2016/5/5.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.limb.customview.view.snow.Random;
import com.limb.customview.view.snow.SnowFlake;

public class SnowView extends View {

    Random random;
    private static final int NUM_SNOWFLAKES = 15;//雪花数量
    private static final int DELAY = 1;//画面延时刷新时间

    private SnowFlake[] snowflakes;//雪花对像数组。

    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void resize(int width, int height) {
        random = new Random();
        snowflakes = new SnowFlake[NUM_SNOWFLAKES];
        for (int i = 0; i < NUM_SNOWFLAKES; i++) {
            //for循环生产雪花。
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(random.getColor(), random.getColor(), random.getColor()));
            //返回的对象存入对象数组中去。
            snowflakes[i] = SnowFlake.create(width, height, paint);
            Log.i("SnowDemo", "时间：：：：：：：：：："+System.currentTimeMillis());
        }
    }

    /**
     * View中方法的启动顺序onSizeChanged()>onDraw();
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize(w, h);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SnowFlake snowFlake : snowflakes) {
            //得到雪花对象，绘制。
            snowFlake.draw(canvas);
        }

        getHandler().postDelayed(runnable, DELAY);//得到子线程，设置5ms延时，每5ms在主线程绘制一次界面。。
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();//此方法会把原来的视图清除掉，并重新调用veiw.onDraw方法。
        }
    };
}
