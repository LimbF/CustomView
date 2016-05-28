package com.limb.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by limb on 2016/5/9.
 */
public class MyCLinerLayoutView extends View {
    private Paint paint;
    private Paint mpaint;

    public MyCLinerLayoutView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(5);
        paint.setTextSize(50);

    }

    public MyCLinerLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCLinerLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawLine(canvas);
    }

    private void onDrawLine(Canvas canvas) {
        canvas.drawLine(100,100,100,1200,mpaint);
        canvas.drawLine(100, 100, 1000, 100, paint);
        canvas.drawCircle(100, 100, 10, paint);

        canvas.drawCircle(100, 300, 30, paint);
        canvas.drawText("已接单",200,300+25,paint);

        canvas.drawLine(200,350,1000,350, paint);

//        for (int i = 100; i < 500; i+=100) {
//            if(i==300){
//                canvas.drawCircle(100,i,30,paint);
//                canvas.drawLine(100,i,200,i,paint);
//                canvas.drawText("已接单",200,i+25,paint);
//            }else {
//                canvas.drawCircle(100,i,10,paint);
//            }
//        }
    }
}
