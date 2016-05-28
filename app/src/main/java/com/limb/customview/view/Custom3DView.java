package com.limb.customview.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by limb on 2016/5/9.
 */
public class Custom3DView extends View {
    private int mCenterX;
    private int mCenterY;
    private float mCanvasRotateX = 0;
    private float mCanvasRotateY = 0;
    private float mCanvasMaxRotateDegree = 360;
    private Matrix mMatrix = new Matrix();
    private Camera mCamera = new Camera();
    private Paint mPaint;

    public Custom3DView(Context context) {
        super(context);
        init();
    }

    public Custom3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Custom3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mCanvasMaxRotateDegree = 360;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        rotateCanvas(canvas);
        canvas.drawCircle(mCenterX, mCenterY, 100, mPaint);
    }
    private void rotateCanvas(Canvas canvas) {
        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(mCanvasRotateX);
        mCamera.rotateY(mCanvasRotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate(-mCenterX, -mCenterY);
        mMatrix.postTranslate(mCenterX, mCenterY);

        canvas.concat(mMatrix);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                rotateCanvasWhenMove(x, y);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                rotateCanvasWhenMove(x, y);
                invalidate();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                mCanvasRotateY = 0;
                mCanvasRotateX = 0;
                invalidate();

                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - mCenterX;
        float dy = y - mCenterY;

        float percentX = dx / mCenterX;
        float percentY = dy /mCenterY;

        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }

        mCanvasRotateY = mCanvasMaxRotateDegree * percentX;
        mCanvasRotateX = -(mCanvasMaxRotateDegree * percentY);
    }
}
