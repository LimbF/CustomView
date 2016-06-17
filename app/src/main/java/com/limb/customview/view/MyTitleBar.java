package com.limb.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limb.customview.R;
import com.limb.customview.utils.DensityUtil;

/**
 * Created by limb on 2016/6/17.
 */
public class MyTitleBar extends RelativeLayout {
    private String leftContent;
    private int leftColor;
    private float leftSize;
    private int leftVisible;//0代表显示，4、8代表隐藏
    private int leftDrawable;

    private String centerContent;
    private int centerColor;
    private float centerSize;
    private int centerVisible;

    private String rightContent;
    private int rightColor;
    private float rightSize;
    private int rightVisible;

    private TextView leftButton;
    private TextView centerTitle;
    private TextView rightButton;

    private MyTitleBarListener myTitileBarListenr;



    public MyTitleBar(Context context) {
        super(context);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //添加到左边
        addLeft(context, attrs);

        //添加到中间
        addCenter(context, attrs);

        //添加到右边
        addRight(context, attrs);

    }



    public MyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 添加右边控件
     * @param context
     * @param attrs
     */
    private void addRight(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        rightSize = ta.getDimension(R.styleable.MyTitleBar_rightTextSize, 19);
        rightColor = ta.getColor(R.styleable.MyTitleBar_rightTextColor, 0);
        rightContent = ta.getString(R.styleable.MyTitleBar_rightTextContent);
        rightVisible = ta.getInteger(R.styleable.MyTitleBar_rightTextVisible,VISIBLE);

        rightButton = new TextView(context);
        LayoutParams paramsRight = new LayoutParams(LayoutParams.WRAP_CONTENT
                , LayoutParams.MATCH_PARENT);
        //放在父类的右边
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsRight.rightMargin = DensityUtil.dip2px(context, 13);
        rightButton.setText(rightContent);
        rightButton.setTextColor(rightColor);
        rightButton.setTextSize(rightSize);
        rightButton.setVisibility(rightVisible);
        rightButton.setGravity(Gravity.CENTER);
        addView(rightButton, paramsRight);

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myTitileBarListenr!=null){
                    myTitileBarListenr.rightListenr();
                }
            }
        });
    }

    /**
     * 添加中间控件
     * @param context
     * @param attrs
     */
    private void addCenter(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        centerSize = ta.getDimension(R.styleable.MyTitleBar_centerTextSize, 19);
        centerColor = ta.getColor(R.styleable.MyTitleBar_centerTextColor, 0);
        centerContent = ta.getString(R.styleable.MyTitleBar_centerTextContent);
        centerVisible = ta.getInteger(R.styleable.MyTitleBar_centerTextVisible,VISIBLE);
        centerTitle = new TextView(context);
        LayoutParams paramsCenter = new LayoutParams(LayoutParams.WRAP_CONTENT
                , LayoutParams.MATCH_PARENT);
        paramsCenter.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerTitle.setText(centerContent);
        centerTitle.setTextColor(centerColor);
        centerTitle.setTextSize(centerSize);
        centerTitle.setGravity(Gravity.CENTER);
        centerTitle.setVisibility(centerVisible);
        addView(centerTitle, paramsCenter);
    }

    /**
     * 添加左边控件
     * @param context
     * @param attrs
     */
    private void addLeft(Context context, AttributeSet attrs) {
        //获取自定义属性类型
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
        //字体大小
        leftSize = ta.getDimension(R.styleable.MyTitleBar_leftTextSize, 17);
        //字体颜色
        leftColor = ta.getColor(R.styleable.MyTitleBar_leftTextColor, 0);
        //控件文本
        leftContent = ta.getString(R.styleable.MyTitleBar_leftTextContent);
        leftVisible = ta.getInteger(R.styleable.MyTitleBar_leftTextVisible, VISIBLE);
//        leftDrawable = ta.getInteger(R.styleable.MyTitleBar_leftDrawable, R.mipmap.ic_launcher);


        //控件对象
        leftButton = new TextView(context);
        LayoutParams paramsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT
                , LayoutParams.MATCH_PARENT);
        paramsLeft.leftMargin = DensityUtil.dip2px(context, 13);
        leftButton.setText(leftContent);
        leftButton.setGravity(CENTER_VERTICAL);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setTextSize(leftSize);
        leftButton.setVisibility(leftVisible);
//        //设置左边图片
//        leftButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                getResources().getDrawable(leftDrawable), null, null, null);
        leftButton.setTextColor(leftColor);
        //添加到父容器
        addView(leftButton, paramsLeft);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTitileBarListenr != null) {
                    myTitileBarListenr.leftListenr();
                }
            }
        });
    }
    public void setTitleBarListener(MyTitleBarListener myTitileBarListenr){
        this.myTitileBarListenr = myTitileBarListenr;
    }


    public interface MyTitleBarListener{
        void leftListenr();
        void rightListenr();
    }

}
