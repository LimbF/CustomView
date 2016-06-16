package com.limb.customview.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by limb on 2016/6/13.
 */
public class ImageViewLoadUtils {
    private boolean isCacheInMemory = true;// 设置下载的图片是否缓存在内存中
    private boolean isCacheOnDisc = true;// 设置下载的图片是否缓存在SD卡中
    private int roundRadius = 0;
    private static ImageViewLoadUtils instance = null;
    public static ImageViewLoadUtils getInstance() {
        if(instance == null) {
            Class var0 = ImageViewLoadUtils.class;
            synchronized(ImageViewLoadUtils.class) {
                if(instance == null) {
                    instance = new ImageViewLoadUtils();
                }
            }
        }

        return instance;
    }

    protected ImageViewLoadUtils() {
    }
    /**
     *
     * @param context 上下文参数
     * @param loadingImgResId 加载中显示的图片
     * @param emptyErImgResId 图片url为null或错误时显示的图片
     * @param failImgResId 设置图片加载或解码过程中发生错误显示的图片
     * @param imageView 要显示的控件对象
     * @param url 图片网络地址
     */
    public void showImage(Context context
            , int loadingImgResId
            , int emptyErImgResId
            , int failImgResId,ImageView imageView,String url) {
        // 初始化ImageLoader
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(loadingImgResId) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(emptyErImgResId) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(failImgResId) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(isCacheInMemory()) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(isCacheOnDisc()) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(roundRadius)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

        ImageLoader.getInstance().displayImage(url,imageView);
    }

    public boolean isCacheInMemory() {
        return isCacheInMemory;
    }

    /**
     * 是否缓存到内存中
     * @param isCacheInMemory
     */
    public void setIsCacheInMemory(boolean isCacheInMemory) {
        this.isCacheInMemory = isCacheInMemory;
    }

    public boolean isCacheOnDisc() {
        return isCacheOnDisc;
    }

    /**
     * 是否缓存到SD卡中
     * @param isCacheOnDisc
     */
    public void setIsCacheOnDisc(boolean isCacheOnDisc) {
        this.isCacheOnDisc = isCacheOnDisc;
    }

    public int getRoundRadius() {
        return roundRadius;
    }

    /**
     * 设置圆角半径
     * @param roundRadius
     */
    public void setRoundRadius(int roundRadius) {
        this.roundRadius = roundRadius;
    }
}
