package com.limb.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.limb.customview.R;

/**
 * Created by limb on 2016/4/25.
 */
public class MyRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private OnScrollEvent onScrollEvent;
    //是否滑动到底
    private boolean isBottom = false;
    private View footerView;
    private int footerViewHeight;//底部加载更多高度
    private View headerView;
    private int headerViewHeight;//头部刷新高度
    private int downY;

    private final int REFRESHING = 0;//正在刷新

    private final int RELEASE_REFRESH = 2;//释放

    private final int DOWN_PULL = 3;//下拉

    private int currentStatus = DOWN_PULL;
    private TextView footerText;
    private TextView headerText;
    private RotateAnimation upAnimation;
    private RotateAnimation downAnimation;
    private ImageView progress;

    public MyRefreshListView(Context context) {
        super(context);
        //初始化头部底部布局
        initFooter(context);
        initHeader(context);
        setOnScrollListener(this);

    }

    public MyRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化头部底部布局
        initFooter(context);
        initHeader(context);
        setOnScrollListener(this);

    }

    public MyRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化头部底部布局
        initFooter(context);
        initHeader(context);
        setOnScrollListener(this);

    }
    /**
     * 初始化头部布局
     * @param context
     */
    private void initHeader(Context context) {
        headerView = LayoutInflater.from(context).inflate(R.layout.listview_header_layout,null);
        headerText = (TextView)headerView.findViewById(R.id.tv_listview_header_text);
        progress = (ImageView)headerView.findViewById(R.id.iv_listview_header_progress);
        // 设置头布局的paddingTop为自己高度的负数
        headerView.measure(0, 0);// 让系统框架去帮我们测量头布局的宽和高.
        // 获得一个测量后的高度, 只有在measure方法被调用完毕后才可以得到具体高度.
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        //添加头部布局到listView头部
        this.addHeaderView(headerView);
        initAnimation();
    }
    /**
     * 初始化脚部布局
     * @param context
     */
    public void initFooter(Context context){
        footerView = LayoutInflater.from(context).inflate(R.layout.listview_footer_layout,null);
        footerText = (TextView)footerView.findViewById(R.id.tv_listview_footer_text);
        // 设置脚布局的paddingTop为自己高度的负数
        footerView.measure(0, 0);// 让系统框架去帮我们测量头布局的宽和高.
        // 获得一个测量后的高度, 只有在measure方法被调用完毕后才可以得到具体高度.
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        //添加脚部布局到listView底部
        this.addFooterView(footerView);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //滑动到底
        if (isBottom && scrollState == 0) {
            onScrollEvent.upPullLoadMore();
            footerView.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 上拉加载完成，隐藏脚部布局
     */
    public void upPullLoadMoreFinish(){
        footerView.setPadding(0, -footerViewHeight, 0, 0);
    }

    /**
     * 下拉刷新完成
     */
    public void downPullLoadMoreFinish(){
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        currentStatus = DOWN_PULL;
        headerText.setText("下拉刷新");
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(totalItemCount<1){
            return;
        }
        int  lastPos = getLastVisiblePosition();
        int count = totalItemCount;
        isBottom = lastPos == count-1;
    }

    /**
     * 初始化接口对象
     * @param onScrollEvent
     */
    public void setOnScrollEvent(OnScrollEvent onScrollEvent){
        if(onScrollEvent!=null){
            this.onScrollEvent = onScrollEvent;
        }
    }
    //滚动监听接口
    public interface OnScrollEvent{
        //上拉加载更多
        void upPullLoadMore();
        //下拉刷新
        void downPullRefresh();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentStatus == REFRESHING){
                    break;
                }
                int moveY = (int) ev.getY();
                int diffY = moveY - downY;
                int paddingTop = -headerViewHeight + diffY;
                int firstP = getFirstVisiblePosition();
                if(paddingTop>-headerViewHeight && firstP == 0){
                    if(paddingTop>0 && currentStatus == DOWN_PULL){
                        currentStatus = RELEASE_REFRESH;
                        refreshHeaderViewState();
                    }else if(paddingTop < 0 && currentStatus == RELEASE_REFRESH) {
                        // 头布局没有完全显示, 并且当前状态是松开刷新, 进入到下拉刷新的状态
                        currentStatus = DOWN_PULL;
                        refreshHeaderViewState();
				    }
                    headerView.setPadding(0, 0, 0, paddingTop);
                    return true;
                }
                progress.startAnimation(upAnimation);
                break;

            case MotionEvent.ACTION_UP:
                if(currentStatus == DOWN_PULL){
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                }else if(currentStatus == RELEASE_REFRESH){
                    //完全显示头部
                    headerView.setPadding(0, 0, 0, 0);
                    //释放状态
                    currentStatus = REFRESHING;
                    refreshHeaderViewState();
                    if(onScrollEvent!=null){
                        //执行下拉刷新
                        onScrollEvent.downPullRefresh();
                        progress.startAnimation(upAnimation);
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 初始化动画
     */
    private void initAnimation() {
        upAnimation = new RotateAnimation(
                0, -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(1000);
        upAnimation.setRepeatCount(-1);
    }
    /**
     * 根据当前的状态currentState来刷新头布局的状态.
     */
    private void refreshHeaderViewState() {
        switch (currentStatus) {
            case DOWN_PULL:	 // 下拉刷新
                headerText.setText("下拉刷新");
                break;
            case RELEASE_REFRESH: // 松开刷新
                headerText.setText("松开刷新");
                break;
            case REFRESHING: // 正在刷新中
                headerText.setText("正在刷新..");
                break;
                default:
                    break;
        }
    }
}
