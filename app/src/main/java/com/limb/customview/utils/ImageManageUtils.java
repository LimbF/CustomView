package com.limb.customview.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by limb on 2016/5/26.
 */
public class ImageManageUtils {
    private WeakHashMap<Integer,WeakReference<Bitmap>> mBitmaps;
    private WeakHashMap<Integer,WeakReference<Drawable>> mDrawables;
    private boolean mActive = true;

    public ImageManageUtils(){
        mBitmaps = new WeakHashMap<>();
        mDrawables = new WeakHashMap<>();
    }
    public Bitmap getBitmaps(int resourse){
        if(mActive){
            if(!mBitmaps.containsKey(resourse)){
                mBitmaps.put(resourse,new WeakReference<>(BitmapFactory.decodeResource(
                        Resources.getSystem(),resourse)));
            }
            return mBitmaps.get(resourse).get();
        }
        return null;
    }
    public Drawable getDrawbles(int resourse){
        if(mActive){
            if(!mDrawables.containsKey(resourse)){
                mDrawables.put(resourse,new WeakReference<>(
                        Resources.getSystem().getDrawable(resourse)));
            }
            return mDrawables.get(resourse).get();
        }

        return null;
    }

    public void recycleBitmaps(){
        Iterator iter = mBitmaps.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry e = (Map.Entry) iter.next();
            ((WeakReference<Bitmap>)e.getValue()).get().recycle();
        }
        mBitmaps.clear();
    }

    public ImageManageUtils setActive(boolean b){
        mActive = b;
        return this;
    }
    public boolean isActive(){
        return mActive;
    }
}
