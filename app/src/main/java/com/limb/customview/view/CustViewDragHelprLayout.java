package com.limb.customview.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by limb on 2016/5/10.
 */
public class CustViewDragHelprLayout extends LinearLayout {
    private ViewDragHelper vdHelper;
    private RelativeLayout rl;
    private Point point = new Point();
    public CustViewDragHelprLayout(Context context) {
        super(context);
        init();
    }

    public CustViewDragHelprLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustViewDragHelprLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        //        1、创建实例
        /**
         * 创建实例需要3个参数，第一个就是当前的ViewGroup，第二个sensitivity，主要用于设置touchSlop:
         * helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
         * 可见传入越大，mTouchSlop的值就会越小。第三个参数就是Callback，在用户的触摸过程中会回调相关方法
         */
        vdHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            /**
             * tryCaptureView如果返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            /**
             * clampViewPositionHorizontal,clampViewPositionVertical可以在该方法中对
             * child移动的边界进行控制，left , top 分别为即将移动到的位置，比如横向的情况下，
             * 我希望只在ViewGroup的内部移动，即：最小>=paddingleft，
             * 最大<=ViewGroup.getWidth()-paddingright-child.getWidth。
             * @param child
             * @param left
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //可拖动的临界面限制（水平方向）
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //垂直方向   限制
//                final int topBound = getPaddingTop();
//                final int bottomBound = getHeight() - child.getHeight() - topBound;
//                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
//                return newTop;
                //直接放回top不限制
                return top;
            }

            /**
             * 手释放的时候调用
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {


                //当手释放的时候，让view回到起点 （不想让其回到原点，就不调用一下方法）

                Log.d("Cust123", "yvel:" + yvel);
                vdHelper.settleCapturedViewAt(point.x, point.y);
                invalidate();
            }

            //使子View消耗事件  如果不重写下列两个方法，则子view点击事件无效(不消耗事件)
            @Override
            public int getViewHorizontalDragRange(View child){
                return getMeasuredWidth()-child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight()-child.getMeasuredHeight();
            }


            /**
             * 当拖拽到状态改变时回调
             * @params 新的状态
             */
            @Override
            public void onViewDragStateChanged(int state) {
                switch (state) {
                    case ViewDragHelper.STATE_DRAGGING:  // 正在被拖动
                        break;
                    case ViewDragHelper.STATE_IDLE:  // view没有被拖拽或者 正在进行fling/snap
                        break;
                    case ViewDragHelper.STATE_SETTLING: // fling完毕后被放置到一个位置
                        break;
                }
                super.onViewDragStateChanged(state);
            }
        });
    }
    //    2、触摸相关方法
    /**
     * onInterceptTouchEvent中通过使用mDragger.shouldInterceptTouchEvent(event)来决定我们是否应该拦截当前的事件。
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return vdHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * onTouchEvent中通过mDragger.processTouchEvent(event)处理事件。
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        vdHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 获取子view对象
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount()>=0){
            rl = (RelativeLayout) getChildAt(0);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        point.x = rl.getLeft();
        point.y = rl.getTop();
    }

    @Override
    public void computeScroll(){
        if(vdHelper.continueSettling(true)) {
            invalidate();
        }
    }


}
